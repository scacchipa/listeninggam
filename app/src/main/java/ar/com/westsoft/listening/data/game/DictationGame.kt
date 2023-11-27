package ar.com.westsoft.listening.data.game

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameStage
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.Keyboard
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.ReaderEngine
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.Utterance
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.VibratorEngine
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.util.toEngine
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.util.toEntity
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.util.normalize
import ar.com.westsoft.listening.util.getIdxPreviousTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictationGame @Inject constructor(
    private val readerEngine: ReaderEngine,
    private val appDatabase: AppDatabase,
    private val keyboard: Keyboard,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val settingsDataStore: DictSettingsDataStore,
    private val vibratorEngine: VibratorEngine,
    private var coroutineScope: CoroutineScope
) {
    var dictationGameRecord: DictationGameRecord? = null

    private val _cursorPosStateFlow = MutableStateFlow(SimpleCursorPos())

    val cursorPosStateFlow = _cursorPosStateFlow as StateFlow<SimpleCursorPos>

    suspend fun setup(gui: Long) {
        dictationGameRecord = getDictationGameRecord(gui)
        _cursorPosStateFlow.emit(SimpleCursorPos())
        gameStageFlow = createGameStageFlow()
    }

    private suspend fun getDictationGameRecord(gui: Long): DictationGameRecord =
        withContext(ioDispatcher) {
            appDatabase
                .getSavedListeningGameDao()
                .getSavedDictationGameEntityList()
                .find {
                    it.gameHeaderEntity.gui == gui
                }
                ?.toEngine()
                ?: throw Exception("GUI didn't find in the database")
        }

    private fun saveDictationProgress(paragraphIdx: Int, gui: Long) {
        CoroutineScope(ioDispatcher).launch {

            val dictationProgressEntity =
                dictationGameRecord?.dictationProgressList?.get(paragraphIdx)?.toEntity()
                    ?: return@launch

            dictationProgressEntity.gameHeaderId = gui

            appDatabase
                .getSavedListeningGameDao()
                .updateDictationProgressEntity(dictationProgressEntity)
        }
    }

    private fun saveGlobalProgressRate() {
        CoroutineScope(ioDispatcher).launch {

            val gameRecord = dictationGameRecord ?: return@launch

            appDatabase
                .getSavedListeningGameDao()
                .updateHeader(
                    gameRecord.gameHeader.copy(
                        progressRate = gameRecord.getGlobalProgressRate()
                    ).toEntity()
                )

        }
    }

    var gameStageFlow: StateFlow<DictGameStage> = createGameStageFlow()

    private fun createGameStageFlow(): StateFlow<DictGameStage> = getReaderEngineFlow().combine(
        flow = _cursorPosStateFlow
    ) { utterance, cursorPos ->
        DictGameStage(
            cursorPos = cursorPos.letterPos,
            paragraphIdx = cursorPos.paragraphIdx,
            utterance = utterance
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Eagerly,
        initialValue = DictGameStage(
            paragraphIdx = 0,
            cursorPos = null,
            utterance = Utterance()
        )
    )

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()

    fun speakOut(
        offset: Int = 0,
        wordCount: Int = runBlocking { settingsDataStore.get(PreferencesKey.ReadWordAfterCursor) },
        rewindWordCount: Int = runBlocking { settingsDataStore.get(PreferencesKey.ReadWordBeforeCursor) }
    ) {
        runBlocking(defaultDispatcher) {
            println("offset: $offset")
            val gameRecord = dictationGameRecord ?: return@runBlocking

            val paragraphNumber = _cursorPosStateFlow.value.paragraphIdx
            val paragraph = gameRecord.dictationProgressList[paragraphNumber]
            readerEngine.speakOut(
                message = paragraph.originalTxt,
                offset = paragraph.progressTxt.getIdxPreviousTo(offset, ' ')?.plus(1) ?: 0,
                utteranceId = paragraphNumber.toString(),
                wordCount = wordCount,
                rewindWordCount = rewindWordCount
            )
        }
    }

    suspend fun moveToParagraph(idx: Int) {
        emitNewParagraphDictationState(idx)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        runBlocking(defaultDispatcher) {
            val gameRecord = dictationGameRecord ?: return@runBlocking

            val currentState = _cursorPosStateFlow.value
            val currentLetterPos = currentState.letterPos

            val paragraphIdx = currentState.paragraphIdx
            val dictationProgress = gameRecord.dictationProgressList[paragraphIdx]

            println("key: ${keyEvent.key.nativeKeyCode}")

            if (keyEvent.type == KeyEventType.KeyDown) {
                when (keyEvent.key) {
                    Key.DirectionRight -> moveNextBlank()
                    Key.DirectionLeft -> _cursorPosStateFlow.emit(
                        currentState.copy(
                            letterPos = dictationProgress.getIdxPreviousBlank(currentLetterPos)
                                ?: dictationProgress.getFirstBlank()
                        )
                    )

                    Key.DirectionDown -> emitNewParagraphDictationState(paragraphIdx + 1)
                    Key.DirectionUp -> emitNewParagraphDictationState(paragraphIdx - 1)
                    Key.Spacebar -> speakOut(offset = currentLetterPos ?: 0)
                    Key.Enter -> moveToParagraph(paragraphIdx + 1)
                    Key.Zero,
                    Key.One,
                    Key.Two,
                    Key.Three,
                    Key.Four,
                    Key.Five,
                    Key.Six,
                    Key.Seven,
                    Key.Eight,
                    Key.Nine,
                    Key.A,
                    Key.B,
                    Key.C,
                    Key.D,
                    Key.E,
                    Key.F,
                    Key.G,
                    Key.H,
                    Key.I,
                    Key.J,
                    Key.K,
                    Key.L,
                    Key.M,
                    Key.N,
                    Key.O,
                    Key.P,
                    Key.Q,
                    Key.R,
                    Key.S,
                    Key.T,
                    Key.U,
                    Key.V,
                    Key.W,
                    Key.X,
                    Key.Y,
                    Key.Z -> checkLetterReveal(keyEvent.key, currentState)

                    Key.Apostrophe -> revealLetter(currentState)
                    Key.Backslash -> revealWord(currentState)
                    Key.Equals -> revealParagraph(currentState.paragraphIdx)
                }
            }
        }
    }

    private suspend fun checkLetterReveal(key: Key, currentState: SimpleCursorPos) {
        if (checkLetter(keyboard.toChar(key), currentState)) {
            revealLetter(currentState)
        }
    }

    private suspend fun revealLetter(currentState: SimpleCursorPos) {
        val gameRecord = dictationGameRecord ?: return
        gameRecord
            .dictationProgressList[currentState.paragraphIdx]
            .setLetterProgress(currentState.letterPos)
        vibratorEngine.vibrareTick()
        saveDictationProgress(currentState.paragraphIdx, gameRecord.gameHeader.gui)
        moveNextBlank()
    }

    private suspend fun revealWord(currentState: SimpleCursorPos) {
        val cursorLetterPos = currentState.letterPos ?: return

        dictationGameRecord
            ?.dictationProgressList?.get(currentState.paragraphIdx)
            ?.revealWord(cursorLetterPos)
        vibratorEngine.vibrareTick()
        moveNextBlank()
    }

    private suspend fun revealParagraph(paragraphIdx: Int) {
        dictationGameRecord
            ?.dictationProgressList?.get(paragraphIdx)
            ?.revealParagraph()

        moveNextBlank()
    }

    private fun checkLetter(char: Char?, currentState: SimpleCursorPos): Boolean {
        val gameRecord = dictationGameRecord ?: return false
        val letterPos = currentState.letterPos ?: return false

        val lineNumber = currentState.paragraphIdx
        val paragraph = gameRecord.dictationProgressList[lineNumber]
        val correctKey = paragraph.originalTxt[letterPos].uppercaseChar().normalize()

        return correctKey == char
    }

    private suspend fun moveNextBlank() {
        val gameRecord = dictationGameRecord ?: return

        val currentState = _cursorPosStateFlow.value

        val nextBlank = currentState.moveNextBlank(gameRecord)

        if (nextBlank != null) {
            _cursorPosStateFlow.emit(nextBlank)
        } else {
            saveGlobalProgressRate()

            _cursorPosStateFlow.emit(currentState)
            _cursorPosStateFlow.emit(
                currentState.moveFirstBlankInNextParagraph(gameRecord)
            )
        }
    }

    private suspend fun emitNewParagraphDictationState(paragraphIdx: Int) {
        val gameRecord = dictationGameRecord ?: return

        val progressList = gameRecord.dictationProgressList

        if (paragraphIdx < 0 || paragraphIdx >= progressList.size) return

        _cursorPosStateFlow.emit(
            SimpleCursorPos(
                letterPos = progressList[paragraphIdx].getFirstBlank(),
                paragraphIdx = paragraphIdx
            )
        )
    }
}

package ar.com.westsoft.listening.data.game

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.mapper.GameHeaderMapper
import ar.com.westsoft.listening.mapper.SavedDictationGameMapper
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameStage
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.Keyboard
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.ReaderEngine
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.VibratorEngine
import ar.com.westsoft.listening.util.char.normalize
import ar.com.westsoft.listening.util.getIdxPreviousTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
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
    private val savedListeningGameMapper: SavedDictationGameMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val settingsDataStore: DictSettingsDataStore,
    private val vibratorEngine: VibratorEngine
) {
    var dictationGameRecord = DictationGameRecord()

    private val dictationViewSharedFlow = MutableSharedFlow<DictationForm>(
        replay = 1,
        extraBufferCapacity = 2
    )

    suspend fun setup(gui: Long) {
        dictationGameRecord = getDictationGameRecord(gui)

        dictationViewSharedFlow.emit(DictationForm())
    }

    private suspend fun getDictationGameRecord(gui: Long): DictationGameRecord =
        withContext(ioDispatcher) {
            savedListeningGameMapper.toEngine(
                appDatabase
                    .getSavedListeningGameDao()
                    .getSavedDictationGameEntityList()
                    .find {
                        it.gameHeaderEntity.gui == gui
                    } ?: throw Exception("GUI didn't find in the database")
            )
        }

    private fun saveDictationProgress(paragraphIdx: Int, gui: Long) {
        CoroutineScope(ioDispatcher).launch {

            val dictationProgressEntity =
                dictationGameRecord.dictationProgressList[paragraphIdx].toEntity()
            dictationProgressEntity.gameHeaderId = gui

            appDatabase
                .getSavedListeningGameDao()
                .updateDictationProgressEntity(dictationProgressEntity)

            appDatabase
                .getSavedListeningGameDao()
                .updateHeader(
                    GameHeaderMapper().toDataSource(
                        dictationGameRecord.gameHeader.copy(
                            progressRate = dictationGameRecord.getGlobalProgressRate()
                        )
                    )
                )
        }
    }

    fun getDictationGameStageFlow(): Flow<DictGameStage> = getReaderEngineFlow().combine(
        flow = getDictationViewFormFlow()
    ) { utterance, dictationForm ->
        DictGameStage(
            cursorPos = dictationForm.cursorLetterPos,
            paragraphIdx = dictationForm.cursorParagraphIdx,
            utterance = utterance,
            charsToShow =  dictationGameRecord
                .dictationProgressList[dictationForm.cursorParagraphIdx]
                .progressTxt,
            dictationGameRecord = dictationGameRecord
        )
    }

    private fun getDictationViewFormFlow() = dictationViewSharedFlow.onEach { dictationForm ->
        saveDictationProgress(
            paragraphIdx = dictationForm.cursorParagraphIdx,
            gui = dictationGameRecord.gameHeader.gui
        )
    }

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()

    fun speakOut(
        offset: Int = 0,
        wordCount: Int = runBlocking { settingsDataStore.get(PreferencesKey.ReadWordAfterCursor) },
        rewindWordCount: Int = runBlocking { settingsDataStore.get(PreferencesKey.ReadWordBeforeCursor) }
    ) {
        runBlocking(defaultDispatcher) {
            println("offset: $offset")
            val paragraphNumber = dictationViewSharedFlow.first().cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[paragraphNumber]
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
            val currentState = dictationViewSharedFlow.first()
            val currentLetterPos = currentState.cursorLetterPos

            val paragraphIdx = currentState.cursorParagraphIdx
            val dictationProgress = dictationGameRecord.dictationProgressList[paragraphIdx]

            println("key: ${keyEvent.key.nativeKeyCode}")

            if (keyEvent.type == KeyEventType.KeyDown) {
                when (keyEvent.key) {
                    Key.DirectionRight -> moveNextBlank()
                    Key.DirectionLeft -> dictationViewSharedFlow.emit(
                        currentState.copy(
                            cursorLetterPos = dictationProgress.getIdxPreviousBlank(currentLetterPos)
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
                    Key.Equals -> revealParagraph(currentState.cursorParagraphIdx)
                }
            }
        }
    }

    private suspend fun checkLetterReveal(key: Key, currentState: DictationForm) {
        if (checkLetter(keyboard.toChar(key), currentState)) {
            revealLetter(currentState)
        }
    }

    private suspend fun revealLetter(currentState: DictationForm) {
        dictationGameRecord
            .dictationProgressList[currentState.cursorParagraphIdx]
            .setLetterProgress(currentState.cursorLetterPos)
        vibratorEngine.vibrareTick()
        moveNextBlank()
    }

    private suspend fun revealWord(currentState: DictationForm) {
        currentState.cursorLetterPos?.let { cursorLetterPos ->
            dictationGameRecord
                .dictationProgressList[currentState.cursorParagraphIdx]
                .revealWord(cursorLetterPos)
            vibratorEngine.vibrareTick()
            moveNextBlank()
        }
    }

    private suspend fun revealParagraph(paragraphIdx: Int) {
        dictationGameRecord
            .dictationProgressList[paragraphIdx]
            .revealParagraph()

        moveNextBlank()
    }

    private fun checkLetter(char: Char?, currentState: DictationForm): Boolean {
        return currentState.cursorLetterPos?.let { pos ->
            val lineNumber = currentState.cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[lineNumber]
            val correctKey = paragraph.originalTxt[pos].uppercaseChar().normalize()

            correctKey == char
        } ?: false
    }

    private suspend fun moveNextBlank() {
        val currentState = dictationViewSharedFlow.first()

        currentState.moveNextBlank(dictationGameRecord)?.let { nextBlank ->
            dictationViewSharedFlow.emit(nextBlank)
        } ?: let {
            dictationViewSharedFlow.emit(currentState)
            dictationViewSharedFlow.emit(
                currentState.moveFirstBlankInNextParagraph(dictationGameRecord)
            )
        }
    }

    private suspend fun emitNewParagraphDictationState(paragraphIdx: Int) {
        val progressList = dictationGameRecord.dictationProgressList

        if (paragraphIdx < 0 || paragraphIdx >= progressList.size) return

        dictationViewSharedFlow.emit(
            DictationForm(
                cursorLetterPos = progressList[paragraphIdx].getFirstBlank(),
                cursorParagraphIdx = paragraphIdx
            )
        )
    }
}

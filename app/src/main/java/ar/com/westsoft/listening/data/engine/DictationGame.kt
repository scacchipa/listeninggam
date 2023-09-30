package ar.com.westsoft.listening.data.engine

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ar.com.westsoft.listening.data.Keyboard
import ar.com.westsoft.listening.data.ReaderEngine
import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.mapper.GameHeaderMapper
import ar.com.westsoft.listening.mapper.SavedDictationGameMapper
import ar.com.westsoft.listening.screen.dictationgame.DictGameSettingsDSO
import ar.com.westsoft.listening.screen.dictationgame.DictGameState
import ar.com.westsoft.listening.util.char.normalize
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.getIdxPreviousTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val settingsDataStore: DictSettingsDataStore
) {
    private var dictationGameRecord = DictationGameRecord()

    private val dictationViewSharedFlow = MutableSharedFlow<DictationState>(
        replay = 1,
        extraBufferCapacity = 2
    )

    private val settingsFlow = MutableStateFlow(
        DictGameSettingsDSO(
            readWordAfterCursorValue = 7,
            readWordBeforeCursorValue = 2
        )
    )

    suspend fun setup(gui: Long) {
        dictationGameRecord = getDictationGameRecord(gui)

        dictationViewSharedFlow.emit(DictationState())
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

    fun getDictationGameStateFlow(): Flow<DictGameState> = getReaderEngineFlow().combine(
        flow = getDictationViewStateFlow()
    ) { utterance, dictationState ->
        DictGameState(
            cursorColumn = dictationState.cursorLetterPos,
            paragraphIdx = dictationState.cursorParagraphIdx,
            textToShow = buildAnnotatedString {
                append(
                    dictationGameRecord
                        .dictationProgressList[dictationState.cursorParagraphIdx]
                        .progressTxt
                        .concatenate()
                )
                if (dictationState.cursorParagraphIdx == utterance.utteranceId?.toInt()) {
                    addStyle(
                        style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                        start = utterance.start,
                        end = utterance.end
                    )
                }
                dictationState.cursorLetterPos?.let { _pos ->
                    addStyle(
                        style = SpanStyle(color = Color.Red),
                        start = _pos,
                        end = _pos + 1
                    )
                }
            },
            dictationGameRecord = dictationGameRecord
        )
    }

    private fun getDictationViewStateFlow() = dictationViewSharedFlow.onEach { dictationState ->
        saveDictationProgress(
            paragraphIdx = dictationState.cursorParagraphIdx,
            gui = dictationGameRecord.gameHeader.gui
        )
    }

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()

    fun getFirstViewState() = DictGameState(
        paragraphIdx = 0,
        cursorColumn = 0,
        textToShow = AnnotatedString("No text yet."),
        dictationGameRecord = DictationGameRecord()
    )

    fun speakOut(offset: Int = 0, wordCount: Int = 8) {
        runBlocking(defaultDispatcher) {
            println("offset: $offset")
            val paragraphNumber = dictationViewSharedFlow.first().cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[paragraphNumber]
            readerEngine.speakOut(
                message = paragraph.originalTxt,
                offset = paragraph.progressTxt.getIdxPreviousTo(offset, ' ') ?: 0,
                utteranceId = paragraphNumber.toString(),
                wordCount = wordCount
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
                    Key.Spacebar -> speakOut(
                        offset = currentLetterPos ?: 0,
                        wordCount = settingsFlow.first().readWordAfterCursorValue
                    )

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
                    Key.Z ->
                        checkLetterReveal(keyEvent.key, currentState)

                    Key.Apostrophe -> revealLetter(currentState)
                    Key.Equals -> revealParagraph(currentState.cursorParagraphIdx)
                }
            }
        }
    }

    private suspend fun checkLetterReveal(key: Key, currentState: DictationState) {
        if (checkLetter(keyboard.toChar(key), currentState)) {
            revealLetter(currentState)
        }
    }

    private suspend fun revealLetter(currentState: DictationState) {
        dictationGameRecord
            .dictationProgressList[currentState.cursorParagraphIdx]
            .setLetterProgress(currentState.cursorLetterPos)

        moveNextBlank()
    }

    private suspend fun revealParagraph(paragraphIdx: Int) {
        val currentProgress = dictationGameRecord.dictationProgressList[paragraphIdx]

        if (currentProgress.isCompleted.not()) {
            (0 until currentProgress.progressTxt.size).forEach { idx ->
                currentProgress.setLetterProgress(idx)
            }
        }

        moveNextBlank()
    }

    private fun checkLetter(char: Char?, currentState: DictationState): Boolean {
        return currentState.cursorLetterPos?.let { _pos ->
            val lineNumber = currentState.cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[lineNumber]
            val correctKey = paragraph.originalTxt[_pos].uppercaseChar().normalize()

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
            DictationState(
                cursorLetterPos = progressList[paragraphIdx].getFirstBlank(),
                cursorParagraphIdx = paragraphIdx
            )
        )
    }
}

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
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.mapper.GameHeaderMapper
import ar.com.westsoft.listening.mapper.SavedDictationGameMapper
import ar.com.westsoft.listening.screen.dictationgame.DictationViewState
import ar.com.westsoft.listening.util.char.normalize
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.getIdxPreviousTo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    private var dictationGameRecord = DictationGameRecord()

    private val dictationViewSharedFlow = MutableSharedFlow<DictationState>(replay = 1)

    suspend fun setup(gui: Long) {
        dictationGameRecord = getDictationGameRecord(gui)

        dictationViewSharedFlow.emit(DictationState())
    }

    private suspend fun getDictationGameRecord(gui: Long): DictationGameRecord = withContext(ioDispatcher) {
        savedListeningGameMapper.toEngine(
            appDatabase
                .getSavedListeningGameDao()
                .getSavedDictationGameEntityList()
                .find {
                    it.gameHeaderEntity.gui == gui
                } ?: throw Exception("GUI didn't find in the database")
        )
    }

    private fun saveDictationProgress(dictationProgress: DictationProgress, gui: Long) {
        CoroutineScope(ioDispatcher).launch {
            val dictationProgressEntity = dictationProgress.toEntity()
            dictationProgressEntity.gameHeaderId = gui

            val progressList = dictationGameRecord.dictationProgressList.filter {
                it.originalTxt.isNotEmpty()
            }
            val totalLinesCount = progressList.size
            val completedLinesCount = progressList.count { progress ->
                progress.originalTxt == progress.progressTxt.concatToString()
            }
            val progressRate: Double = completedLinesCount.toDouble() / totalLinesCount

            appDatabase
                .getSavedListeningGameDao()
                .updateDictationProgressEntity(dictationProgressEntity)

            appDatabase
                .getSavedListeningGameDao()
                .updateHeader(
                    GameHeaderMapper().toDataSource(
                        dictationGameRecord.gameHeader.copy(
                            progressRate = progressRate
                        )
                    )
                )
        }
    }

    fun getDictationGameStateFlow(): Flow<DictationViewState> = getReaderEngineFlow().combine(
            flow = getDictationViewStateFlow()
        ) { utterance, dictationState ->
        DictationViewState(
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
                dictationState.cursorLetterPos?.let {_pos ->
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

    private fun getDictationViewStateFlow() = dictationViewSharedFlow

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()

    fun getFirstViewState() = DictationViewState(
        paragraphIdx = 0,
        cursorColumn = 0,
        textToShow = AnnotatedString("No text yet."),
        dictationGameRecord= DictationGameRecord()

    )

    fun speakOut(offset: Int = 0, wordCount: Int? = 8) {
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
        val currentState = dictationViewSharedFlow.first()

        saveDictationProgress(
            dictationProgress = dictationGameRecord.dictationProgressList[currentState.cursorParagraphIdx],
            gui = dictationGameRecord.gameHeader.gui)
        emitNewParagraphDictationState(idx)
    }
    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        runBlocking(defaultDispatcher) {
            val currentState = dictationViewSharedFlow.first()
            val currentLetterPos = currentState.cursorLetterPos

            val gui = dictationGameRecord.gameHeader.gui
            val paragraphIdx = currentState.cursorParagraphIdx
            val dictationProgress = dictationGameRecord.dictationProgressList[paragraphIdx]

            if (keyEvent.type == KeyEventType.KeyDown) {
                when (keyEvent.key) {
                    Key.DirectionRight -> {
                        saveDictationProgress(dictationProgress, gui)
                        dictationViewSharedFlow.emit(
                            currentState.moveNextBlank(dictationGameRecord)
                        )
                    }
                    Key.DirectionLeft -> {
                        dictationViewSharedFlow.emit(
                            currentState.copy(
                                cursorLetterPos = dictationProgress.getIdxPreviousBlank(currentLetterPos)
                                    ?: dictationProgress.getFirstBlank()
                            )
                        )
                    }
                    Key.DirectionDown -> {
                        saveDictationProgress(dictationProgress, gui)
                        emitNewParagraphDictationState(paragraphIdx + 1)
                    }
                    Key.DirectionUp -> {
                        saveDictationProgress(dictationProgress, gui)
                        emitNewParagraphDictationState(paragraphIdx - 1)
                    }
                    Key.Spacebar-> {
                        speakOut(
                            offset = currentLetterPos ?: 0,

                        )
                    }
                    Key.Enter -> {
                        saveDictationProgress(dictationProgress, gui)
                        moveToParagraph(paragraphIdx + 1)
                    }
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

    private fun checkLetter(char: Char?, currentState: DictationState): Boolean {
        return currentState.cursorLetterPos?.let { _pos ->
            val lineNumber = currentState.cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[lineNumber]
            val correctKey = paragraph.originalTxt[_pos].uppercaseChar().normalize()

            correctKey == char
        } ?: false
    }


    private suspend fun moveNextBlank() {
        dictationViewSharedFlow.emit(
            dictationViewSharedFlow.first().moveNextBlank(dictationGameRecord)
        )
    }

    private suspend fun emitNewParagraphDictationState(paragraphIdx: Int) {
        val progressList = dictationGameRecord.dictationProgressList

        if (paragraphIdx < 0 || paragraphIdx  >= progressList.size) return

        dictationViewSharedFlow.emit(
            DictationState(
                cursorLetterPos = progressList[paragraphIdx].getFirstBlank(),
                cursorParagraphIdx = paragraphIdx
            )
        )
    }
}

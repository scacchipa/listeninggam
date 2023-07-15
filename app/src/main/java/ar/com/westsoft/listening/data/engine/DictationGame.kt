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
import ar.com.westsoft.listening.mapper.SavedDictationGameMapper
import ar.com.westsoft.listening.screen.dictationgame.DictationViewState
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.getIdxFirst
import ar.com.westsoft.listening.util.getIdxNextTo
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

        dictationViewSharedFlow.emit(DictationState(0))
    }

    private suspend fun getDictationGameRecord(gui: Long): DictationGameRecord = withContext(ioDispatcher) {
        savedListeningGameMapper.toEngine(
            appDatabase
                .getSavedListeningGameDao()
                .getSavedDictationGameDtoList()
                .find {
                    it.gameHeaderEntity.gui == gui
                } ?: throw Exception("GUI didn't find in the database")
        )
    }

    private fun saveDictationProgress(dictationProgress: DictationProgress, gui: Long) {
        CoroutineScope(ioDispatcher).launch {
            val dictationProgressEntity = dictationProgress.toEntity()
            dictationProgressEntity.gameHeaderId = gui

            appDatabase
                .getSavedListeningGameDao()
                .updateDictationProgressEntity(dictationProgressEntity)
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
            })
    }

    private fun getDictationViewStateFlow() = dictationViewSharedFlow

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()


    fun getFirstViewState() = DictationViewState(
        paragraphIdx = 0,
        cursorColumn = 0,
        textToShow = AnnotatedString("No text yet.")
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

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        runBlocking(defaultDispatcher) {
            val currentState = dictationViewSharedFlow.first()
            val currentLetterPos = currentState.cursorLetterPos

            val gui = dictationGameRecord.gameHeader.gui
            val paragraphIdx = currentState.cursorParagraphIdx
            val dictationProgress = dictationGameRecord.dictationProgressList[paragraphIdx]

            if (keyEvent.type == KeyEventType.KeyDown) {
                println(keyEvent.key.nativeKeyCode)
                when (keyEvent.key) {
                    Key.DirectionRight -> dictationViewSharedFlow.emit(currentState.copy(
                            cursorLetterPos = dictationProgress.progressTxt.getIdxNextTo(currentLetterPos, '_')
                                ?: dictationProgress.progressTxt.getIdxFirst('_')
                        ))
                    Key.DirectionLeft -> {
                        dictationViewSharedFlow.emit(
                            currentState.copy(
                                cursorLetterPos = dictationProgress.progressTxt.getIdxPreviousTo(currentLetterPos, '_')
                                    ?: dictationProgress.progressTxt.getIdxFirst('_')
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
                        checkLetterReveal(keyEvent.key, currentLetterPos)
                }
            }
        }
    }

    private suspend fun checkLetterReveal(key: Key, pos: Int?) {
        pos?.let { _pos ->

            val lineNumber = dictationViewSharedFlow.first().cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[lineNumber]
            val correctKey = paragraph.originalTxt[_pos].uppercaseChar()

            if (correctKey == keyboard.toChar(key)) {
                paragraph.setLetterProgress(_pos)
                moveNextBlank()
            }
        }
    }

    private suspend fun moveNextBlank() {
        val currentState = dictationViewSharedFlow.first()
        val paragraph = dictationGameRecord.dictationProgressList[currentState.cursorParagraphIdx]
        val progressTxt = paragraph.progressTxt
        val currentLetterPos = currentState.cursorLetterPos

        dictationViewSharedFlow.emit(currentState.copy(
            cursorLetterPos = progressTxt.getIdxNextTo(currentLetterPos, '_')
                ?: paragraph.progressTxt.getIdxFirst('_')
        ))
    }

    private suspend fun emitNewParagraphDictationState(paragraphIdx: Int) {
        val progressList = dictationGameRecord.dictationProgressList

        if (paragraphIdx < 0 || paragraphIdx  >= progressList.size) return

        val newProgress = progressList[paragraphIdx].progressTxt

        dictationViewSharedFlow.emit(
            DictationState(
                cursorLetterPos = newProgress.getIdxFirst('_'),
                cursorParagraphIdx = paragraphIdx
            )
        )
    }
}

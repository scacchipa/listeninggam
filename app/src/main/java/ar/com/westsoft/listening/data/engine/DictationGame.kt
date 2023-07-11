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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
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

    private val dictationViewSharedFlow = MutableSharedFlow<DictationViewState>(replay = 1)

    suspend fun setup(gui: Long) {
        dictationGameRecord = getDictationGameRecord(gui)

        dictationViewSharedFlow.emit(DictationViewState(0))
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

    fun getDictationGameStateFlow(): Flow<AnnotatedString> = getReaderEngineFlow().combine(
            flow = getDictationViewStateFlow()
        ) { utterance, dictationViewState ->
        buildAnnotatedString {
            append(
                dictationGameRecord
                    .dictationProgressList[dictationViewState.cursorParagraphIdx]
                    .progressTxt
                    .joinToString("")
            )
            if (dictationViewState.cursorParagraphIdx == utterance.utteranceId?.toInt()) {
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                    start = utterance.start,
                    end = utterance.end
                )
            }
            addStyle(
                style = SpanStyle(color = Color.Red),
                start = dictationViewState.cursorLetterPos,
                end = dictationViewState.cursorLetterPos + 1
            )
        }
    }

    private fun getDictationViewStateFlow() = dictationViewSharedFlow

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()


    fun getFirstAnnotatedString(): AnnotatedString = AnnotatedString("No text yet.")

    fun speakOut(offset: Int = 0) {
        runBlocking(defaultDispatcher) {
            val paragraphNumber = dictationViewSharedFlow.first().cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[paragraphNumber]
            readerEngine.speakOut(
                message = paragraph.originalTxt,
                offset = paragraph.progressTxt.getIdxPreviousTo(offset, ' ') ?: 0,
                utteranceId = paragraphNumber.toString()
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        runBlocking(defaultDispatcher) {
            val currentState = dictationViewSharedFlow.first()
            val currentLetterPos = currentState.cursorLetterPos

            val paragraphIdx = currentState.cursorParagraphIdx
            val paragraph = dictationGameRecord.dictationProgressList[paragraphIdx]

            if (keyEvent.type == KeyEventType.KeyDown) {
                when (keyEvent.key) {
                    Key.DirectionRight -> dictationViewSharedFlow.emit(currentState.copy(
                            cursorLetterPos = paragraph.progressTxt.getIdxNextTo(currentLetterPos, '_')
                                ?:currentLetterPos
                        ))
                    Key.DirectionLeft ->
                        dictationViewSharedFlow.emit(currentState.copy(
                            cursorLetterPos = paragraph.progressTxt.getIdxPreviousTo(currentLetterPos, '_')
                                ?:currentLetterPos
                        ))
                    Key.DirectionDown ->
                        dictationViewSharedFlow.emit(currentState.copy(
                            cursorParagraphIdx = currentState.cursorParagraphIdx + 1,
                            cursorLetterPos = 0
                        ))
                    Key.DirectionUp ->
                        dictationViewSharedFlow.emit(currentState.copy(
                            cursorParagraphIdx = currentState.cursorParagraphIdx + 1,
                            cursorLetterPos = 0
                        ))
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
                    Key.Z -> checkLetterReveal(keyEvent.key, currentLetterPos)
                }
            }
        }
    }

    private suspend fun checkLetterReveal(key: Key, pos: Int) {
        val lineNumber = dictationViewSharedFlow.first().cursorParagraphIdx
        val paragraph = dictationGameRecord.dictationProgressList[lineNumber]
        val correctKey = paragraph.originalTxt[pos].uppercaseChar()

        if (correctKey == keyboard.toChar(key)) {
            paragraph.setLetterProgress(pos)
            moveNextBlank()
        }
    }

    private suspend fun moveNextBlank() {
        val currentState = dictationViewSharedFlow.first()
        val paragraph = dictationGameRecord.dictationProgressList[currentState.cursorParagraphIdx]
        val progressTxt = paragraph.progressTxt
        val currentLetterPos = currentState.cursorLetterPos

        dictationViewSharedFlow.emit(currentState.copy(
            cursorLetterPos = progressTxt.getIdxNextTo(currentLetterPos, '_')
                ?: currentLetterPos
        ))
    }
}

fun CharArray.getIdxNextTo(idx: Int, char: Char): Int? {
    var pos = idx + 1

    while (pos < this.size) {
        if (this[pos] == char) return pos
        pos += 1
    }
    return null
}

fun CharArray.getIdxPreviousTo(idx: Int, char: Char): Int? {
    var pos = idx - 1

    while (pos >= 0) {
        if (this[pos] == char) return pos
        pos -= 1
    }
    return null
}
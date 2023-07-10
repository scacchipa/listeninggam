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

    private val dictationViewStateFlow = MutableSharedFlow<DictationViewState>()

    var cursorParagraphNumber: Int = 0
    var cursorLetterPos: Int = 0

    suspend fun setup(gui: Long) {
        dictationGameRecord = getGame(gui)
        cursorParagraphNumber = 0

        dictationViewStateFlow.emit(DictationViewState(0))
    }

    private suspend fun getGame(gui: Long): DictationGameRecord = withContext(ioDispatcher) {
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
                    .dictationProgressList[dictationViewState.showedParagraphIdx]
                    .progressTxt
                    .joinToString("")
            )
            if (dictationViewState.showedParagraphIdx == cursorParagraphNumber) {
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                    start = utterance.start,
                    end = utterance.end
                )
                addStyle(
                    style = SpanStyle(color = Color.Red),
                    start = cursorLetterPos,
                    end = cursorLetterPos + 1
                )
            }
        }
    }

    private fun getDictationViewStateFlow() = dictationViewStateFlow

    private fun getReaderEngineFlow() = readerEngine.getUtteranceFlow()


    fun getFirstAnnotatedString(): AnnotatedString = AnnotatedString("No text yet.")

    fun speakOut(offset: Int = 0) {
        readerEngine.speakOut(
            message = dictationGameRecord.dictationProgressList[0].originalTxt,
            offset = findPreviousSpace(offset)
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        if (keyEvent.type == KeyEventType.KeyDown) {
            when (keyEvent.key) {
                Key.DirectionRight -> {
                    if (cursorLetterPos < dictationGameRecord.dictationProgressList[0].originalTxt.length) {
                        cursorLetterPos += 1
                    }
                    moveNextBlank()
                }
                Key.DirectionLeft -> {
                    if (cursorLetterPos > 0) cursorLetterPos -= 1
                    movePreviousBlank()
                }
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
                Key.Z -> checkLetterReveal(keyEvent.key, cursorLetterPos)
            }
        }
    }

    private fun checkLetterReveal(key: Key, pos: Int) {
        cursorParagraphNumber?.let { _lineNumber ->
            val correctKey = dictationGameRecord.dictationProgressList[_lineNumber].originalTxt[pos].uppercaseChar()

            if (correctKey == keyboard.toChar(key)) {
                dictationGameRecord.dictationProgressList[_lineNumber].setLetterProgress(pos)
                moveNextBlank()
            }
        }
    }

    private fun moveNextBlank() {
        while (
            cursorLetterPos < dictationGameRecord.dictationProgressList[0].originalTxt.length
            && dictationGameRecord.dictationProgressList[0].getProgress()[cursorLetterPos] != '_'
        ) {
            cursorLetterPos += 1
        }
    }

    private fun movePreviousBlank() {
        while (
            cursorLetterPos > 0
            && dictationGameRecord.dictationProgressList[0].getProgress()[cursorLetterPos] != '_'
        ) {
            cursorLetterPos -= 1
        }
    }

    private fun findPreviousSpace(letterPos: Int): Int {
        println("Character of starting: $letterPos")
        var idx = letterPos
        while (
            idx > 0
            && dictationGameRecord.dictationProgressList[0].getProgress()[idx] != ' '
        ) {
            idx -= 1
        }
        println("Previous Space character: $idx")
        return idx
    }
}

data class DictationViewState(
    val showedParagraphIdx: Int
)
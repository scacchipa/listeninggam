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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
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
    private var game = Game()

    var selectedLineNumber: Int? = null
    var selectedLetterPos: Int = 0

    suspend fun setup(gui: Long) {
        game = getGame(gui)
        selectedLineNumber = if (game.dictationProgressList.isNotEmpty()) 0 else null
    }

    private suspend fun getGame(gui: Long): Game = withContext(ioDispatcher) {
        savedListeningGameMapper.toEngine(
            appDatabase
                .getSavedListeningGameDao()
                .getSavedDictationGameDtoList()
                .find {
                    it.gameHeaderEntity.gui == gui
                } ?: throw Exception("GUI didn't find in the database")
        )
    }

    fun getAnnotatedStringFlow() =
        readerEngine.getUtteranceFlow()
            .filterNotNull()
            .map { utterance ->
                buildAnnotatedString {
                    selectedLineNumber?.let { _selectedLineNumber ->
                        append(
                            game
                                .dictationProgressList[_selectedLineNumber]
                                .progressTxt
                                .joinToString("")
                        )
                        addStyle(
                            style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                            start = utterance.start,
                            end = utterance.end
                        )
                        addStyle(
                            style = SpanStyle(color = Color.Red),
                            start = selectedLetterPos,
                            end = selectedLetterPos + 1
                        )
                    }
                }
            }

    fun getFirstAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            game.dictationProgressList.let {
                if (it.isNotEmpty())
                    append(it[0].getProgress().joinToString(""))
                else
                    append("")
            }
        }
    }

    fun speakOut(offset: Int = 0) {
        readerEngine.speakOut(
            message = game.dictationProgressList[0].originalTxt,
            offset = findPreviousSpace(offset)
        )
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        if (keyEvent.type == KeyEventType.KeyDown) {
            when (keyEvent.key) {
                Key.DirectionRight -> {
                    if (selectedLetterPos < game.dictationProgressList[0].originalTxt.length) {
                        selectedLetterPos += 1
                    }
                    moveNextBlank()
                }
                Key.DirectionLeft -> {
                    if (selectedLetterPos > 0) selectedLetterPos -= 1
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
                Key.Z -> checkLetterReveal(keyEvent.key, selectedLetterPos)
            }
        }
    }

    private fun checkLetterReveal(key: Key, pos: Int) {
        selectedLineNumber?.let { _lineNumber ->
            val correctKey = game.dictationProgressList[_lineNumber].originalTxt[pos].uppercaseChar()

            if (correctKey == keyboard.toChar(key)) {
                game.dictationProgressList[_lineNumber].setLetterProgress(pos)
                moveNextBlank()
            }
        }
    }

    private fun moveNextBlank() {
        while (
            selectedLetterPos < game.dictationProgressList[0].originalTxt.length
            && game.dictationProgressList[0].getProgress()[selectedLetterPos] != '_'
        ) {
            selectedLetterPos += 1
        }
    }

    private fun movePreviousBlank() {
        while (
            selectedLetterPos > 0
            && game.dictationProgressList[0].getProgress()[selectedLetterPos] != '_'
        ) {
            selectedLetterPos -= 1
        }
    }

    private fun findPreviousSpace(letterPos: Int): Int {
        println(letterPos)
        var idx = letterPos
        while (
            idx > 0
            && game.dictationProgressList[0].getProgress()[idx] != ' '
        ) {
            idx -= 1
        }
        println(idx)
        return idx
    }
}

package ar.com.westsoft.listening.service

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ar.com.westsoft.listening.data.DictationProgress
import ar.com.westsoft.listening.data.Keyboard
import ar.com.westsoft.listening.data.ReaderEngine
import ar.com.westsoft.listening.data.SpeechProvider
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictationGame @Inject constructor(
    private val readerEngine: ReaderEngine,
    private val dictationProgress: DictationProgress,
    private val speechProvider: SpeechProvider,
    private val keyboard: Keyboard
) {

    var selectedLetterPos: Int = 0
    fun initialize() {
        dictationProgress.initProgress(speechProvider.getSpeech())
    }

    fun getAnnotatedStringFlow() =
        readerEngine.getUtteranceFlow()
            .filterNotNull()
            .map { utterance ->
                buildAnnotatedString {
                    append(String(dictationProgress.getProgress()))
                    addStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold),
                        start = utterance.start,
                        end = utterance.end,
                    )
                    addStyle(
                        style = SpanStyle(color = Color.Red),
                        start = selectedLetterPos,
                        end = selectedLetterPos + 1
                    )
                }
            }

    fun speakOutAll() {
        readerEngine.speakOut(dictationProgress.getAllText())
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun onKeyEvent(keyEvent: KeyEvent) {
        if (keyEvent.type == KeyEventType.KeyDown) {
            when (keyEvent.key) {
                Key.DirectionRight -> {
                    if (selectedLetterPos < dictationProgress.getAllText().length) {
                        selectedLetterPos += 1
                    }
                    moveNextBlankSpace()
                }
                Key.DirectionLeft -> {
                    if (selectedLetterPos > 0) selectedLetterPos -= 1
                    movePreviousBlankSpace()
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
        println("***** " + dictationProgress.getAllText()[pos].uppercaseChar())
        println("***** " + keyboard.toChar(key))

        if (dictationProgress.getAllText()[pos].uppercaseChar() == keyboard.toChar(key)) {
            dictationProgress.setLetterProgress(pos)
            moveNextBlankSpace()
        }
    }

    private fun moveNextBlankSpace() {
        while (
            selectedLetterPos < dictationProgress.getAllText().length
            && dictationProgress.getProgress()[selectedLetterPos] != '_'
        ) {
            selectedLetterPos += 1
        }
    }

    private fun movePreviousBlankSpace() {
        while (
            selectedLetterPos > 0
            && dictationProgress.getProgress()[selectedLetterPos] != '_'
        ) {
            selectedLetterPos -= 1
        }
    }
}

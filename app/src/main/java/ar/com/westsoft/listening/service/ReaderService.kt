package ar.com.westsoft.listening.service

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ar.com.westsoft.listening.data.ReaderEngine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReaderService @Inject constructor(
    private val readerEngine: ReaderEngine
) {
    var speech: String? = null

    fun getAnnotatedStringFlow() =
        readerEngine.getUtteranceFlow()
            .filterNotNull()
            .map { utterance ->
                buildAnnotatedString {
                    append(speech ?: "")
                    addStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold),
                        start = utterance.start,
                        end = utterance.end,
                    )
                }
            }

    fun speakOut(message: String) {
        speech = message
        readerEngine.speakOut(message)
    }
}

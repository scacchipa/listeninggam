package ar.com.westsoft.listening.data

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import ar.com.westsoft.listening.util.takeWords
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class ReaderEngine @Inject constructor(
    context: Application
) {
    private val tts: TextToSpeech by lazy {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.ERROR) {
                Log.i("SpeechToText", "Synthesizer init error")
            } else if (status == TextToSpeech.SUCCESS) {
                Log.i("SpeechToText", "Synthesizer init Success")
                tts.language = Locale.US
                tts.setPitch(1f)
                tts.setSpeechRate(1f)
            }
        }
    }
    var offset: Int = 0

    fun getUtteranceFlow() = flow {
        emit(Utterance())
        while (true) {
            emit(tts.awaitUtterance(this@ReaderEngine))
            println("Utterance emitted")
        }
    }

    fun speakOut(message: String, offset: Int = 0, utteranceId: String = "", wordCount: Int?) {
        this.offset = offset
        println("Offset speakOut: ${this.offset}")

        val msg = message
            .substring(offset)
            .takeWords(wordCount)
            .replace("_", "", false)
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }
}

data class Utterance(
    val utteranceId: String? = null,
    val start: Int = 0,
    val end: Int = 0,
    val frame: Int = 0,
)

suspend fun TextToSpeech.awaitUtterance(readerEngine: ReaderEngine) =
    suspendCancellableCoroutine {
        val listener = object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                println("*** onStart SpeakOut")
            }

            override fun onDone(utteranceId: String?) {
                println("*** onDone SpeakOut")
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                println("*** onError SpeakOut")
            }

            override fun onRangeStart(
                utteranceId: String?,
                start: Int,
                end: Int,
                frame: Int,
            ) {
                println("*** onRangeStart: utteranceId=$utteranceId, start=${start + readerEngine.offset}, end=${end + readerEngine.offset}, frame=$frame")
                it.resume(Utterance(utteranceId, start + readerEngine.offset, end + readerEngine.offset, frame))
            }
        }
        it.invokeOnCancellation {
            setOnUtteranceProgressListener(null)
        }
        setOnUtteranceProgressListener(listener)
    }

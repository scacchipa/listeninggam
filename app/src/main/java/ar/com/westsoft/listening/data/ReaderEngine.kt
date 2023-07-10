package ar.com.westsoft.listening.data

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
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
    private val tts: TextToSpeech = TextToSpeech(
        context
    ) { status ->
        if (status == TextToSpeech.ERROR) {
            Log.i("SpeechToText", "Synthesizer init error")
        } else if (status == TextToSpeech.SUCCESS) {
            Log.i("SpeechToText", "Synthesizer init Success")
        }
    }
    private var offset: Int = 0

    fun getUtteranceFlow() = flow {
        emit(Utterance())
        while (true) {
            emit(tts.awaitUtterance(offset))
        }
    }

    init {
        tts.setPitch(1f)
        tts.setSpeechRate(0.5f)
        tts.setVoice(
            Voice(
                "en-us-x-sfg#male-local",
                Locale.US,
                400,
                200,
                true,
                hashSetOf("male"),
            ),
        )
    }

    fun speakOut(message: String, offset: Int = 0) {
        this.offset = offset
        tts.speak(message.substring(offset), TextToSpeech.QUEUE_FLUSH, null, "")
    }
}

data class Utterance(
    val utteranceId: String? = null,
    val start: Int = 0,
    val end: Int = 0,
    val frame: Int = 0,
)

suspend fun TextToSpeech.awaitUtterance(offset: Int) =
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
                println("*** onRangeStart: utteranceId=$utteranceId, start=$start, end=$end, frame=$frame")
                it.resume(Utterance(utteranceId, start + offset, end + offset, frame))
            }
        }
        it.invokeOnCancellation {
            setOnUtteranceProgressListener(null)
        }
        setOnUtteranceProgressListener(listener)
    }

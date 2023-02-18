package ar.com.westsoft.listening.data

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import ar.com.westsoft.listening.speech
import java.util.Locale
import javax.inject.Inject

class Reader @Inject constructor(
    private val context: Context
) : OnInitListener {

    val tts: TextToSpeech = TextToSpeech(context, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.ERROR) {
            Log.i("SpeechToText", "Sintetizer init error")
        } else if (status == TextToSpeech.SUCCESS) {
            Log.i("SpeechToText", "Sintetizer init Success")
            println(tts.setPitch(1f))
            println(tts.setSpeechRate(1f))
            println(
                tts.setVoice(
                    Voice(
                        "en-us-x-sfg#male-local",
                        Locale.US,
                        400,
                        200,
                        true,
                        hashSetOf("male")
                    )
                )
            )
            tts.setOnUtteranceProgressListener(
                object : UtteranceProgressListener() {
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
                        frame: Int
                    ) {
                        println("*** onRangeStart: utteranceId=$utteranceId, start=$start, end=$end, frame=$frame")
                        println(
                            speech.substring(
                                start until end
                            )
                        )
                    }
                }
            )
        }
    }

    fun speakOut(message: String) {
        tts?.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        println("SpeakOut")
    }
}

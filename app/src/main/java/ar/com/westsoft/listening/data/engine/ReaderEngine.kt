package ar.com.westsoft.listening.data.engine

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.datasource.toSetting
import ar.com.westsoft.listening.util.Constants
import ar.com.westsoft.listening.util.rewindWordsOrFirst
import ar.com.westsoft.listening.util.takeWords
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class ReaderEngine @Inject constructor(
    context: Application,
    private val settingsDataStore: DictSettingsDataStore,
    private val coroutineScope: CoroutineScope
) {
    private var settings = Constants.DICT_SETTINGS_DATA_STORE_DEFAULT.toSetting()

    private fun getSettingsDataStoreFlow() = settingsDataStore
        .getDictGameSettingsDSOFlow()
        .map { it.toSetting() }

    private val tts: TextToSpeech by lazy {
        TextToSpeech(context) { status ->
            if (status == TextToSpeech.ERROR) {
                Log.i("SpeechToText", "Synthesizer init error")
            } else if (status == TextToSpeech.SUCCESS) {
                Log.i("SpeechToText", "Synthesizer init Success")
                tts.language = Locale.US
                tts.setSpeechRate(calculateSpeechRate())
            }
        }
    }

    init {
        coroutineScope.launch {
            getSettingsDataStoreFlow().collect { collector ->
                this@ReaderEngine.settings = collector
                tts.setSpeechRate(calculateSpeechRate())
                println("setSpeechRate: ${collector.speechRatePercentage}%")
            }
        }
    }

    var offset: Int = 0

    protected fun finalize() {
        coroutineScope.cancel()
    }

    fun getUtteranceFlow() = flow {
        emit(Utterance())
        while (true) {
            emit(tts.awaitUtterance(this@ReaderEngine))
            println("Utterance emitted")
        }
    }

    fun speakOut(
        message: String,
        offset: Int = 0,
        utteranceId: String = "",
        wordCount: Int,
        rewindWordCount: Int = 0
    ) {
        this.offset = message.rewindWordsOrFirst(offset, rewindWordCount) ?: offset
        println("Offset speakOut: ${this.offset}")

        val msg = message
            .substring(this.offset)
            .takeWords(wordCount)
            .replace("_", "", false)
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    private fun calculateSpeechRate() =
        settings.speechRatePercentage.value / 100 * getSpeedLevelFactor()

    private fun getSpeedLevelFactor(): Float =
        when (settings.speedLevel.value){
            SpeedLevelPreference.LOW_SPEED_LEVEL -> 0.50f
            SpeedLevelPreference.MEDIUM_SPEED_LEVEL -> 0.75f
            SpeedLevelPreference.NORMAL_SPEED_LEVEL -> 1.00f
            SpeedLevelPreference.HIGH_SPEED_LEVEL -> 1.25f
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
                it.resume(
                    Utterance(
                        utteranceId,
                        start + readerEngine.offset,
                        end + readerEngine.offset,
                        frame
                    )
                )
            }
        }
        it.invokeOnCancellation {
            setOnUtteranceProgressListener(null)
        }
        setOnUtteranceProgressListener(listener)
    }

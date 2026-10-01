package ar.com.westsoft.listening.data.engine

import android.media.AudioTrack
import android.util.Log
import android.util.LruCache
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.datasource.toSetting
import ar.com.westsoft.listening.util.Constants
import ar.com.westsoft.listening.util.rewindWordsOrFirst
import ar.com.westsoft.listening.util.takeWords
import com.k2fsa.sherpa.onnx.GeneratedAudio
import com.k2fsa.sherpa.onnx.OfflineTts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class ReaderEngine @Inject constructor(
    private val settingsDataStore: DictSettingsDataStore,
    private val coroutineScope: CoroutineScope,
    private val tts: OfflineTts,
    private val audioTrackManager: AudioTrackManager
) {

    private var settings = Constants.DICT_SETTINGS_DATA_STORE_DEFAULT.toSetting()
    private val _utteranceFlow = MutableSharedFlow<Utterance>(extraBufferCapacity = 1)

    fun getUtteranceFlow() = _utteranceFlow.asSharedFlow()

    private val audioCache = LruCache<Pair<String, Float>, GeneratedAudio>(20)

    private var audioJob: Job? = null
    private fun getSettingsDataStoreFlow() = settingsDataStore
        .getDictGameSettingsDSOFlow()
        .map { it.toSetting() }

    init {
        coroutineScope.launch {
            getSettingsDataStoreFlow().collect { collector ->
                this@ReaderEngine.settings = collector
                println("setSpeechRate: ${collector.speechRatePercentage}%")
            }
        }
    }

    var offset: Int = 0

    protected fun finalize() {
        coroutineScope.cancel()
        tts.release()
        audioTrackManager.release()
    }

    fun speakOut(
        message: String,
        offset: Int = 0,
        utteranceId: String = "",
        wordCount: Int,
        rewindWordCount: Int = 0
    ) {
        val startPos = message.rewindWordsOrFirst(offset, rewindWordCount) ?: offset
        this.offset = startPos
        val msgWithPunctuation = message.substring(startPos).takeWords(wordCount)
        val end = startPos + msgWithPunctuation.length
        val msg = msgWithPunctuation.replace("_", "", false)

        Log.d("ReaderEngine", "speakOut: msg='$msg', offset=$startPos, end=$end")

        audioJob?.cancel()

        audioJob = coroutineScope.launch(Dispatchers.Default) {
            if (isActive) {
                _utteranceFlow.emit(
                    Utterance(
                        utteranceId = utteranceId,
                        start = startPos,
                        end = end
                    )
                )
            }

            val speed = calculateSpeechRate()
            val cacheKey = msg to speed

            val audio = audioCache.get(cacheKey)
                ?: run {
                    Log.d("ReaderEngine", "Generating audio for: '$msg' at speed $speed")
                    val generatedAudio = tts.generate(msg, 0, speed)

                    if (generatedAudio.samples.isEmpty()) {
                        Log.w("ReaderEngine", "Generated audio samples are empty")
                        return@launch
                    }

                    audioCache.put(cacheKey, generatedAudio)
                    generatedAudio
                }

            playAudio(audio.samples, audio.sampleRate, utteranceId)
        }
    }

    private suspend fun playAudio(
        samples: FloatArray,
        sampleRate: Int,
        utteranceId: String
    ) = withContext(Dispatchers.IO) {

        Log.d("ReaderEngine", "playAudio: samples=${samples.size}, rate=$sampleRate, id=$utteranceId")

        try {
            val track = audioTrackManager.getTrack(sampleRate)

            track.pause()
            track.flush()
            track.play()

            Log.d("ReaderEngine", "AudioTrack started playing (PCM FLOAT)")

            val chunkSize = 4096
            var written = 0
            while (written < samples.size && isActive) {
                val toWrite = minOf(chunkSize, samples.size - written)
                val res = track.write(samples, written, toWrite, AudioTrack.WRITE_BLOCKING)
                if (res <= 0) break
                written += res
            }

            if (isActive) {
                // Wait for the track to finish playing
                val durationMs = (samples.size.toFloat() / sampleRate * 1000).toLong()
                delay((durationMs + 100).milliseconds)
            }

            Log.d("ReaderEngine", "Playback finished")
        } catch (e: Exception) {
            Log.e("ReaderEngine", "Error during playback", e)
        }
    }

    private fun calculateSpeechRate() =
        settings.speechRatePercentage / 100f * getSpeedLevelFactor()

    private fun getSpeedLevelFactor(): Float =
        when (settings.speedLevel){
            SpeedLevelPreference.LOW_SPEED_LEVEL -> 0.50f
            SpeedLevelPreference.MEDIUM_SPEED_LEVEL -> 0.75f
            SpeedLevelPreference.NORMAL_SPEED_LEVEL -> 1.00f
            SpeedLevelPreference.HIGH_SPEED_LEVEL -> 1.25f
            SpeedLevelPreference.VERY_HIGH_SPEED_LEVEL -> 1.50f
            SpeedLevelPreference.MAX_SPEED_LEVEL -> 2.00f
        }
}

data class Utterance(
    val utteranceId: String? = null,
    val start: Int = 0,
    val end: Int = 0,
    val frame: Int = 0,
)

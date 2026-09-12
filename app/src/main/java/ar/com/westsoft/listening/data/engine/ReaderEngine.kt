package ar.com.westsoft.listening.data.engine

import android.media.AudioAttributes
import android.media.AudioFormat
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
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class ReaderEngine @Inject constructor(
    private val settingsDataStore: DictSettingsDataStore,
    private val coroutineScope: CoroutineScope,
    private val tts: OfflineTts
) {

    private var settings = Constants.DICT_SETTINGS_DATA_STORE_DEFAULT.toSetting()
    private val _utteranceFlow = MutableSharedFlow<Utterance>(extraBufferCapacity = 1)

    fun getUtteranceFlow() = _utteranceFlow.asSharedFlow()
    private var audioTrack: AudioTrack? = null

    private val audioCache = LruCache<Pair<String, Float>, GeneratedAudio>(20)

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
        audioTrack?.release()
    }

    suspend fun speakOut(
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

        withContext(Dispatchers.Default) {
            val speed = calculateSpeechRate()
            val cacheKey = msg to speed

            val audio = audioCache.get(cacheKey)
                ?: run {
                    Log.d("ReaderEngine", "Generating audio for: '$msg' at speed $speed")
                    val generatedAudio = tts.generate(msg, 0, speed)

                    if (generatedAudio.samples.isEmpty()) {
                        Log.w("ReaderEngine", "Generated audio samples are empty")
                        return@withContext
                    }

                    audioCache.put(cacheKey, generatedAudio)
                    generatedAudio
                }

            playAudio(audio.samples, audio.sampleRate, utteranceId, end)
        }
    }

    private suspend fun playAudio(
        samples: FloatArray,
        sampleRate: Int,
        utteranceId: String,
        end: Int
    ) = withContext(Dispatchers.IO) {
        val maxVal = samples.maxOrNull() ?: 0f
        val minVal = samples.minOrNull() ?: 0f
        Log.d("ReaderEngine", "playAudio: samples=${samples.size}, rate=$sampleRate, id=$utteranceId, max=$maxVal, min=$minVal")

        val oldTrack = audioTrack
        audioTrack = null
        oldTrack?.let {
            try {
                it.stop()
                it.release()
            } catch (e: Exception) {
                Log.e("ReaderEngine", "Error releasing old track", e)
            }
        }

        val minBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_FLOAT
        )

        val bufferSize = minBufferSize.coerceAtLeast(samples.size * 4)

        try {
            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_FLOAT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            track.play()
            audioTrack = track
            Log.d("ReaderEngine", "AudioTrack started playing (PCM FLOAT)")

            val written = track.write(samples, 0, samples.size, AudioTrack.WRITE_BLOCKING)
            Log.d("ReaderEngine", "Written $written samples to AudioTrack")

            _utteranceFlow.emit(
                Utterance(
                    utteranceId = utteranceId,
                    start = this@ReaderEngine.offset,
                    end = end
                )
            )

            // Wait for the track to finish playing
            val durationMs = (samples.size.toFloat() / sampleRate * 1000).toLong()
            delay((durationMs + 100).milliseconds)

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
        }
}

data class Utterance(
    val utteranceId: String? = null,
    val start: Int = 0,
    val end: Int = 0,
    val frame: Int = 0,
)

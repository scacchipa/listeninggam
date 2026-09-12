package ar.com.westsoft.listening.data.engine

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioTrackManager @Inject constructor() {
    private var audioTrack: AudioTrack? = null

    fun getTrack(sampleRate: Int): AudioTrack {

        val track = audioTrack

        if (track?.sampleRate == sampleRate) {
            return track
        }

        audioTrack?.let {
            try {
                it.stop()
                it.release()
            } catch (e: Exception) {
                Log.e("AudioTrackManager", "Error releasing AudioTrack", e)
            }
        }

        val minBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_FLOAT
        )

        val bufferSize = minBufferSize * 4

        val newTrack = AudioTrack.Builder()
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

        newTrack.play()

        Log.d("AudioTrackManager", "Created new AudioTrack with sampleRate: $sampleRate")

        audioTrack = newTrack

        return newTrack
    }

    fun release() {
        audioTrack?.let {
            try {
                it.stop()
                it.release()
            } catch (e: Exception) {
                Log.e("AudioTrackManager", "Error on release", e)
            }
        }
        audioTrack = null
    }
}

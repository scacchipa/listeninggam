package ar.com.westsoft.listening.di

import android.content.Context
import android.util.Log
import com.k2fsa.sherpa.onnx.OfflineTts
import com.k2fsa.sherpa.onnx.OfflineTtsConfig
import com.k2fsa.sherpa.onnx.OfflineTtsModelConfig
import com.k2fsa.sherpa.onnx.OfflineTtsVitsModelConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpeakerProvider {

    @Singleton
    @Provides
    fun provideOfflineTts(
        context: Context
    ): OfflineTts {
        val ttsDir = File(context.filesDir, "tts/vits-piper-en_US-amy-low")

        val config = OfflineTtsConfig(
            model = OfflineTtsModelConfig(
                vits = OfflineTtsVitsModelConfig(
                    model = File(ttsDir, "en_US-amy-low.onnx").absolutePath,
                    tokens = File(ttsDir, "tokens.txt").absolutePath,
                    dataDir = File(ttsDir, "espeak-ng-data").absolutePath,
                    lexicon = ""
                ),
                numThreads = 1,
                debug = true
            )
        )
        Log.d("SpeakerProvider", "Initializing OfflineTts with config: $config")
        return try {
            OfflineTts(null, config)
        } catch (e: Exception) {
            Log.e("SpeakerProvider", "Failed to initialize OfflineTts", e)
            throw e
        }
    }
}

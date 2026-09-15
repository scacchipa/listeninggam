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
import java.io.FileOutputStream
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpeakerProvider {

    @Singleton
    @Provides
    fun provideOfflineTts(
        context: Context
    ): OfflineTts {
        val ttsDir = File(context.filesDir, "tts")
        
        // Ensure fresh copy if not completed
        if (!File(ttsDir, "completed").exists()) {
            Log.d("SpeakerProvider", "Copying TTS assets to ${ttsDir.absolutePath}")
            ttsDir.deleteRecursively()
            ttsDir.mkdirs()
            copyAssetFolder(context, "tts", context.filesDir)
            File(ttsDir, "completed").createNewFile()
        }

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

    private fun copyAssetFolder(context: Context, assetFolderName: String, destinationDir: File) {
        val assetManager = context.assets
        val assets = assetManager.list(assetFolderName) ?: return

        if (assets.isEmpty()) {
            // It's a file
            copyAssetFile(context, assetFolderName, File(destinationDir, assetFolderName))
        } else {
            // It's a directory
            val dir = File(destinationDir, assetFolderName)
            if (!dir.exists()) dir.mkdirs()
            for (asset in assets) {
                copyAssetFolder(context, "$assetFolderName/$asset", destinationDir)
            }
        }
    }

    private fun copyAssetFile(context: Context, assetFilePath: String, destinationFile: File) {
        destinationFile.parentFile?.mkdirs()
        try {
            context.assets.open(assetFilePath).use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: Exception) {
            Log.e("SpeakerProvider", "Error copying asset file: $assetFilePath", e)
        }
    }
}

package ar.com.westsoft.listening.di

import android.content.Context
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
        copyAssets(context, "espeak-ng-data")

        val config = OfflineTtsConfig(
            model = OfflineTtsModelConfig(
                vits = OfflineTtsVitsModelConfig(
                    model = "en_US-amy-low.onnx",
                    lexicon = "",
                    tokens = "tokens.txt",
                    dataDir = "${context.filesDir.absolutePath}/espeak-ng-data"
                ),
                numThreads = 1,
                debug = true
            )
        )
        return OfflineTts(context.assets, config)
    }

    private fun copyAssets( context: Context, path: String) {
        val assets = context.assets
        val files = assets.list(path)
        if (files.isNullOrEmpty()) {
            // It is a file
            val outPath = File(context.filesDir, path)
            if (outPath.exists()) return
            assets.open(path).use { inputStream ->
                FileOutputStream(outPath).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } else {
            // It is a directory
            val outDir = File(context.filesDir, path)
            if (!outDir.exists()) outDir.mkdirs()
            for (file in files) {
                copyAssets(context, "$path/$file")
            }
        }
    }
}
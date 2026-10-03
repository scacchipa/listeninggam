package ar.com.westsoft.listening.component

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.com.westsoft.listening.R
import ar.com.westsoft.listening.screen.ListeningTheme
import ar.com.westsoft.listening.screen.menu.NavigationScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListeningTheme {
                var isSplashing by remember { mutableStateOf(true) }
                var progress by remember { mutableFloatStateOf(0f) }
                var statusText by remember { mutableStateOf("Checking resources...") }

                val context = applicationContext

                LaunchedEffect(Unit) {
                    val ttsDir = File(context.filesDir, "tts")
                    val completedFile = File(ttsDir, "completed")

                    if (completedFile.exists()) {
                        statusText = "Ready"
                        progress = 1f
                        delay(1000.milliseconds)
                        isSplashing = false
                    } else {
                        statusText = "Downloading voice model..."
                        val downloaded = withContext(Dispatchers.IO) {
                            try {
                                ttsDir.deleteRecursively()
                                ttsDir.mkdirs()

                                val archiveFile = File(ttsDir, "vits-piper-en_US-amy-low.tar.bz2")
                                val url = "https://huggingface.co/buckets/scacchipa/read_write_public/resolve/vits-piper-en_US-amy-low.tar.bz2?download=true"

                                val client = OkHttpClient.Builder()
                                    .readTimeout(120, TimeUnit.SECONDS)
                                    .connectTimeout(30, TimeUnit.SECONDS)
                                    .build()
                                val request = Request.Builder().url(url).build()
                                val response = client.newCall(request).execute()
                                if (!response.isSuccessful) {
                                    Log.e("MainActivity", "Download failed with code: ${response.code}")
                                    return@withContext false
                                }
                                val body = response.body ?: return@withContext false
                                val contentLength = body.contentLength()
                                val inputStream = body.byteStream()
                                val outputStream = FileOutputStream(archiveFile)

                                val buffer = ByteArray(8192)
                                var bytesRead: Long = 0
                                var read: Int
                                while (inputStream.read(buffer).also { read = it } != -1) {
                                    outputStream.write(buffer, 0, read)
                                    bytesRead += read
                                    progress =
                                        if (contentLength > 0)
                                            (bytesRead.toFloat() / contentLength.toFloat()) * 0.8f
                                        else
                                            minOf(progress + 0.005f, 0.79f)
                                }
                                outputStream.flush()
                                outputStream.close()
                                inputStream.close()
                                response.close()

                                Log.d("MainActivity", "Download completed. Size: ${archiveFile.length()} bytes")

                                statusText = "Extracting files..."
                                progress = 0.85f

                                BufferedInputStream(FileInputStream(archiveFile)).use { bis ->
                                    BZip2CompressorInputStream(bis).use { bzis ->
                                        TarArchiveInputStream(bzis).use { tais ->
                                            var entry = tais.nextTarEntry
                                            var count = 0
                                            while (entry != null) {
                                                val entryName = entry.name
                                                if (entryName.isNotBlank() && entryName != "./") {
                                                    val f = File(ttsDir, entryName)
                                                    count++
                                                    if (entry.isDirectory || entryName.endsWith("/")) {
                                                        f.mkdirs()
                                                        val logMsg = "Extracted Dir [$count]: $entryName"
                                                        Log.d("MainActivity", logMsg)
                                                        statusText = logMsg
                                                    } else {
                                                        val buffer = ByteArray(327680)
                                                        var read: Int
                                                        f.parentFile?.mkdirs()
                                                        BufferedOutputStream(FileOutputStream(f), 327680).use { bos ->
                                                            while (tais.read(buffer).also { read = it } != -1) {
                                                                bos.write(buffer, 0, read)
                                                            }
                                                            bos.flush()
                                                        }
                                                        val logMsg = "Extracted File [$count]: $entryName"
                                                        Log.d("MainActivity", logMsg)
                                                        statusText = logMsg
                                                        progress = 0.85f + (minOf(count.toFloat() / 150f, 1f) * 0.14f)
                                                    }
                                                }
                                                entry = tais.nextTarEntry
                                            }
                                        }
                                    }
                                }

                                archiveFile.delete()
                                completedFile.createNewFile()
                                progress = 1f
                                true
                            } catch (e: Exception) {
                                Log.e("MainActivity", "Error downloading/extracting TTS model", e)
                                e.printStackTrace()
                                false
                            }
                        }

                        if (downloaded) {
                            delay(500.milliseconds)
                            isSplashing = false
                        } else {
                            statusText = "Error downloading resources. Retrying..."
                            delay(2000.milliseconds)
                        }
                    }
                }

                if (isSplashing) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.logo_leer_escribir),
                                        contentDescription = "Logo",
                                        tint = Color(0xFFF5F5F5), // Almost white
                                        modifier = Modifier.size(150.dp)
                                    )
                                    Text(
                                        text = statusText,
                                        color = Color.LightGray,
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                }
                            }

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp, vertical = 24.dp),
                                color = Color.White,
                                trackColor = Color.DarkGray
                            )
                        }
                    }
                } else {
                    NavigationScreen()
                }
            }
        }
    }
}

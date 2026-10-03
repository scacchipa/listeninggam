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
import androidx.compose.material3.MaterialTheme
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
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListeningTheme {
                var isSplashing by remember { mutableStateOf(true) }
                var downloadProgress by remember { mutableFloatStateOf(0f) }
                var extractionProgress by remember { mutableFloatStateOf(0f) }
                var statusText by remember { mutableStateOf("Checking resources...") }

                val context = applicationContext
                val ttsDir = remember { File(context.filesDir, "tts") }
                val completedFile = remember { File(ttsDir, "completed") }

                LaunchedEffect(Unit) {
                    val url = "https://huggingface.co/buckets/scacchipa/read_write_public/resolve/vits-piper-en_US-amy-low.tar.bz2?download=true"

                    if (completedFile.exists()) {
                        statusText = ""
                        downloadProgress = 1f
                        extractionProgress = 1f
                        delay(3000.milliseconds) // 3 seconds pause when files already exist
                        isSplashing = false
                    } else {
                        statusText = "Downloading & Extracting..."
                        val downloaded = downloadAndExtractTts(
                            ttsDir = ttsDir,
                            url = url,
                            completedFile = completedFile,
                            onDownloadProgress = { downloadProgress = it },
                            onExtractionProgress = { extractionProgress = it },
                        )
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

                            if (!completedFile.exists()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp, vertical = 24.dp)
                                ) {
                                    Text(
                                        text = "Download",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    LinearProgressIndicator(
                                        progress = { downloadProgress },
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.White,
                                        trackColor = Color.DarkGray
                                    )

                                    Text(
                                        text = "Extraction",
                                        color = Color.Gray,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                                    )
                                    LinearProgressIndicator(
                                        progress = { extractionProgress },
                                        modifier = Modifier.fillMaxWidth(),
                                        color = Color.White,
                                        trackColor = Color.DarkGray
                                    )
                                }
                            }
                        }
                    }
                } else {
                    NavigationScreen()
                }
            }
        }
    }
}

private suspend fun downloadAndExtractTts(
    ttsDir: File,
    url: String,
    completedFile: File,
    onDownloadProgress: (Float) -> Unit,
    onExtractionProgress: (Float) -> Unit
): Boolean = withContext(Dispatchers.IO) {
    try {
        ttsDir.deleteRecursively()
        ttsDir.mkdirs()

        val pipedIn = PipedInputStream(65536)
        val pipedOut = PipedOutputStream(pipedIn)

        val downloadJob = async(Dispatchers.IO) {
            downloadTtsModel(url, pipedOut, onDownloadProgress)
        }

        val extractJob = async(Dispatchers.IO) {
            extractTtsModel(ttsDir, pipedIn, onExtractionProgress)
        }

        val downloadSuccess = downloadJob.await()
        val extractSuccess = extractJob.await()

        if (downloadSuccess && extractSuccess) {
            completedFile.createNewFile()
            onDownloadProgress(1f)
            onExtractionProgress(1f)
            true
        } else {
            false
        }
    } catch (e: Exception) {
        Log.e("MainActivity", "Error downloading/extracting TTS model", e)
        e.printStackTrace()
        false
    }
}

private fun downloadTtsModel(
    url: String,
    pipedOut: PipedOutputStream,
    onDownloadProgress: (Float) -> Unit
): Boolean {
    return try {
        val client = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        if (!response.isSuccessful) {
            Log.e("MainActivity", "Download failed with code: ${response.code}")
            try { pipedOut.close() } catch (_: Exception) {}
            return false
        }
        val body = response.body
        if (body == null) {
            try { pipedOut.close() } catch (_: Exception) {}
            return false
        }
        val contentLength = body.contentLength()
        val inputStream = body.byteStream()

        pipedOut.use { out ->
            val buffer = ByteArray(8192)
            var bytesRead: Long = 0
            var read: Int
            while (inputStream.read(buffer).also { read = it } != -1) {
                out.write(buffer, 0, read)
                bytesRead += read
                if (contentLength > 0) {
                    onDownloadProgress(bytesRead.toFloat() / contentLength.toFloat())
                } else {
                    onDownloadProgress(0.5f)
                }
            }
            out.flush()
        }
        inputStream.close()
        response.close()
        onDownloadProgress(1f)
        true
    } catch (e: Exception) {
        Log.e("MainActivity", "Error downloading TTS model", e)
        try { pipedOut.close() } catch (_: Exception) {}
        false
    }
}

private fun extractTtsModel(
    ttsDir: File,
    pipedIn: PipedInputStream,
    onExtractionProgress: (Float) -> Unit,
): Boolean {
    return try {
        pipedIn.use { pin ->
            BufferedInputStream(pin, 65536).use { bis ->
                BZip2CompressorInputStream(bis).use { bzis ->
                    TarArchiveInputStream(bzis).use { tais ->
                        var entry = tais.nextEntry
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
                                    onExtractionProgress(minOf(count.toFloat() / 150f, 1f))
                                }
                            }
                            entry = tais.nextEntry
                        }
                    }
                }
            }
        }
        onExtractionProgress(1f)
        true
    } catch (e: Exception) {
        Log.e("MainActivity", "Error extracting TTS model", e)
        false
    }
}

package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExternalApi @Inject constructor(
    private val client: OkHttpClient,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun downloadFile(url: String): String? = withContext(ioDispatcher) {
        println("Download: $url")
        val request = Request.Builder()
            .url(url)
            .build()
        return@withContext client
            .newCall(request)
            .execute()
            .body()?.string()
    }
}

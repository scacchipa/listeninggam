package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.ExternalApi
import javax.inject.Inject

class FileRepository @Inject constructor(
    private val externalApi: ExternalApi
){
    suspend fun downloadFile(url: String): String? {
        return externalApi.downloadFile(url)
    }
}
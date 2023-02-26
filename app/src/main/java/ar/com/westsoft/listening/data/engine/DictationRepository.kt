package ar.com.westsoft.listening.data.engine

import ar.com.westsoft.listening.data.datasource.ExternalApi
import javax.inject.Inject


// https://www.gutenberg.org/files/74/74-0.txt --> The Adventures of Tom Sawyer, by Mark Twain
class DictationRepository @Inject constructor(
    private val externalApi: ExternalApi
) {
    suspend fun createADictationGame(url: String)  {
        println(externalApi.downloadFile(url))
    }
}
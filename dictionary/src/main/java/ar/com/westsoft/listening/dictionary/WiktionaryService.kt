package ar.com.westsoft.listening.dictionary

import retrofit2.http.GET
import retrofit2.http.Path

interface WiktionaryService {
    @GET("api/rest_v1/page/definition/{word}")
    suspend fun getDefinition(@Path("word") word: String): Map<String, List<WiktionaryItem>>
}

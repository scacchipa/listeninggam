package ar.com.westsoft.listening.dictionary.di

import ar.com.westsoft.listening.dictionary.source.WiktionaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DictionaryOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object DictionaryNetworkModule {

    @Provides
    @Singleton
    @DictionaryOkHttpClient
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("User-Agent", "ListeningGameApp/1.0 (ar.com.westsoft.listening; contact@westsoft.com.ar)")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@DictionaryOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://en.wiktionary.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWiktionaryService(retrofit: Retrofit): WiktionaryService {
        return retrofit.create(WiktionaryService::class.java)
    }
}

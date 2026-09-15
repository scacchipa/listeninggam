package ar.com.westsoft.listening.dictionary.di

import android.content.Context
import androidx.room.Room
import ar.com.westsoft.listening.dictionary.database.DictionaryDatabase
import ar.com.westsoft.listening.dictionary.screen.DictionaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DictionaryDatabase {
        return Room.databaseBuilder(
            context,
            DictionaryDatabase::class.java,
            "dictionary_database"
        ).build()
    }

    @Provides
    fun provideDictionaryDao(database: DictionaryDatabase): DictionaryDao {
        return database.dictionaryDao()
    }
}
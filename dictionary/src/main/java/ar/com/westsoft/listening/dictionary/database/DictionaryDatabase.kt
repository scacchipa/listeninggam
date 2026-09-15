package ar.com.westsoft.listening.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoredDefinition::class], version = 1)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}
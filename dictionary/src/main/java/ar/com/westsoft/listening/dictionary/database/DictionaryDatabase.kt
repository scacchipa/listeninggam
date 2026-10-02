package ar.com.westsoft.listening.dictionary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StoredDefinition::class], version = 2)
@TypeConverters(DictionaryConverters::class)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun dictionaryDao(): DictionaryDao
}
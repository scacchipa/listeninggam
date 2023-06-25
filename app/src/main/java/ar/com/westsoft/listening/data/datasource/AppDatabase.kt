package ar.com.westsoft.listening.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DictationProgressEntity::class, GameHeaderEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getSavedListeningGameDao(): SavedDictationGameDao
}
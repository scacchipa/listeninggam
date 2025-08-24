package ar.com.westsoft.listening.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.com.westsoft.listening.data.datasource.dao.SavedDictationGameDao
import ar.com.westsoft.listening.data.datasource.entity.DictationProgressEntity
import ar.com.westsoft.listening.data.datasource.entity.GameHeaderEntity

@Database(
    entities = [DictationProgressEntity::class, GameHeaderEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getSavedListeningGameDao(): SavedDictationGameDao
}
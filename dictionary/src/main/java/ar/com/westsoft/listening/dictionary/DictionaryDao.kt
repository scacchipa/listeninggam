package ar.com.westsoft.listening.dictionary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {
    @Query("SELECT * FROM dictionary_definitions WHERE word = :word")
    suspend fun getDefinition(word: String): StoredDefinition?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definition: StoredDefinition)
}

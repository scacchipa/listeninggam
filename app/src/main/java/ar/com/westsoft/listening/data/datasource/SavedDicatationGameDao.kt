package ar.com.westsoft.listening.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedDicatationGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listeningGame: SavedDictationGameDto): Long

    @Query("SELECT * FROM LISTENING_GAME_TABLE")
    fun getAllSavedListeningGame(): List<SavedDictationGameDto>

    @Query("SELECT * FROM LISTENING_GAME_TABLE WHERE gui = :gui")
    fun getSavedListeningGame(gui: Long): SavedDictationGameDto
}

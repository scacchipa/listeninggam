package ar.com.westsoft.listening.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SavedDictationGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeader(gameHeaderDto: GameHeaderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProgress(progressDtoList: List<DictationProgressEntity>)

    @Transaction
    fun insertGameHeaderAndProgress(
        gameHeaderEntity: GameHeaderEntity,
        progressDtoList: List<DictationProgressEntity>
    ) : Long {
        val headerId = insertHeader(gameHeaderEntity)

        progressDtoList.forEach {
            it.gameHeaderId = headerId
        }

        insertProgress(progressDtoList)

        return headerId
    }

    fun insertGameDto(game: SavedDictationGameEntity): Long {
        return insertGameHeaderAndProgress(
            gameHeaderEntity = game.gameHeaderEntity,
            progressDtoList = game.dictationProgressEntityLists
        )
    }

    @Transaction
    @Query("SELECT * FROM GAME_HEADER")
    fun getSavedDictationGameDtoList(): List<SavedDictationGameEntity>
}

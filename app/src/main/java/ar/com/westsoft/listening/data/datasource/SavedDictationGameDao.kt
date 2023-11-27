package ar.com.westsoft.listening.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface SavedDictationGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeader(gameHeaderEntity: GameHeaderEntity): Long

    @Update
    fun updateHeader(gameHeaderEntity: GameHeaderEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProgress(progressEntityList: List<DictationProgressEntity>)

    @Transaction
    fun insertGameHeaderAndProgress(
        gameHeaderEntity: GameHeaderEntity,
        progressEntityList: List<DictationProgressEntity>
    ) : Long {
        val headerId = insertHeader(gameHeaderEntity)

        progressEntityList.forEach {
            it.gameHeaderId = headerId
        }

        insertProgress(progressEntityList)

        return headerId
    }

    fun insertGameEntity(game: SavedDictationGameEntity): Long {
        return insertGameHeaderAndProgress(
            gameHeaderEntity = game.gameHeaderEntity,
            progressEntityList = game.dictationProgressEntityLists
        )
    }

    @Update
    fun updateDictationProgressEntity(dictionaryProgressEntity: DictationProgressEntity)

    @Transaction
    @Query("SELECT * FROM GameHeaderEntity")
    fun getSavedDictationGameEntityList(): List<SavedDictationGameEntity>

    @Transaction
    @Delete
    fun deleteWholeGame(gameHeaderEntity: GameHeaderEntity): Int
}

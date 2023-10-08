package ar.com.westsoft.listening.data.datasource

import androidx.room.Embedded
import androidx.room.Relation

data class SavedDictationGameEntity(
    @Embedded val gameHeaderEntity: GameHeaderEntity,
    @Relation(
        parentColumn = "gui",
        entityColumn = "gameHeaderId",
        entity = DictationProgressEntity::class,
    )
    val dictationProgressEntityLists: List<DictationProgressEntity>
)

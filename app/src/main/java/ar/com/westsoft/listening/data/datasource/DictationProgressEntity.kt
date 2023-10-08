package ar.com.westsoft.listening.data.datasource

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = GameHeaderEntity::class,
            parentColumns = ["gui"],
            childColumns = ["gameHeaderId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class DictationProgressEntity(
    @PrimaryKey(autoGenerate = true)
    var progressId: Long?,

    var gameHeaderId: Long,

    val originalTxt: String,

    val progressTxt: String
)

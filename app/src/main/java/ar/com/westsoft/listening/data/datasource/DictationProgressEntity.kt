package ar.com.westsoft.listening.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DictationProgressEntity (
    @PrimaryKey(autoGenerate = true)
    var progressId: Long?,

    var gameHeaderId: Long,

    val originalTxt: String,

    val progressTxt: String
)

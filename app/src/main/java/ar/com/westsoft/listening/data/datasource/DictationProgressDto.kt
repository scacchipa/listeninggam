package ar.com.westsoft.listening.data.datasource

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class DictationProgressDto (
    @ColumnInfo(name = "original_text")
    val originalTxt: String,

    @ColumnInfo(name = "progression_text")
    val progressTxt: String
)


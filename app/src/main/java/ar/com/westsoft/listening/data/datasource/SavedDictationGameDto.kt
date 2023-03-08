package ar.com.westsoft.listening.data.datasource

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LISTENING_GAME_TABLE")
data class SavedDictationGameDto(
    @PrimaryKey(autoGenerate = true) val gui: Long,
    @ColumnInfo(name = "username") val title: String,
    @ColumnInfo(name = "text_address") val txtAddress: String,
    @Embedded val dictationProgressDto: DictationProgressDto
)


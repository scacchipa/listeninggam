package ar.com.westsoft.listening.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GAME_HEADER")
data class GameHeaderEntity(
    @PrimaryKey(autoGenerate = true) val gui: Long,
    val title: String,
    val txtAddress: String
)
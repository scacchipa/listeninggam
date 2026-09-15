package ar.com.westsoft.listening.dictionary

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary_definitions")
data class StoredDefinition(
    @PrimaryKey val word: String,
    val definition: String,
    val timestamp: Long = System.currentTimeMillis()
)

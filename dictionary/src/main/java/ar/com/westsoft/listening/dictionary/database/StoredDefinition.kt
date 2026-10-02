package ar.com.westsoft.listening.dictionary.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ar.com.westsoft.listening.dictionary.source.WiktionaryItem

@Entity(tableName = "dictionary_definitions")
data class StoredDefinition(
    @PrimaryKey val word: String,
    val items: List<WiktionaryItem>,
    val timestamp: Long = System.currentTimeMillis()
)
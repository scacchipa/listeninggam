package ar.com.westsoft.listening.dictionary.repository

import android.util.Log
import ar.com.westsoft.listening.dictionary.database.DictionaryDao
import ar.com.westsoft.listening.dictionary.database.StoredDefinition
import ar.com.westsoft.listening.dictionary.source.WiktionaryItem
import ar.com.westsoft.listening.dictionary.source.WiktionaryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryManager @Inject constructor(
    private val wiktionaryService: WiktionaryService,
    private val dictionaryDao: DictionaryDao
) {
    suspend fun getDefinition(word: String): List<WiktionaryItem> {
        val lowercaseWord = word.lowercase().trim()

        // 1. Check local database first
        val cached = dictionaryDao.getDefinition(lowercaseWord)
        if (cached != null) {
            Log.d("DictionaryManager", "Found cached definition for $lowercaseWord")
            return cached.items
        }

        // 2. If not found, fetch from API
        return try {
            Log.d("DictionaryManager", "Fetching definition for $lowercaseWord from API")
            val response = wiktionaryService.getDefinition(lowercaseWord)
            val englishItems = response["en"] ?: return emptyList()

            // Normalize whitespace inside definitions before saving
            val processedItems = englishItems.map { item ->
                item.copy(
                    definitions = item.definitions.map { def ->
                        def.copy(definition = def.definition.replace(Regex("\\s+"), " ").trim())
                    }.filter { it.definition.isNotBlank() }
                )
            }.filter { it.definitions.isNotEmpty() }

            if (processedItems.isNotEmpty()) {
                // 3. Save to local database
                dictionaryDao.insert(StoredDefinition(lowercaseWord, processedItems))
            }
            processedItems
        } catch (e: Exception) {
            Log.e("DictionaryManager", "Error fetching definition for $lowercaseWord", e)
            emptyList()
        }
    }
}
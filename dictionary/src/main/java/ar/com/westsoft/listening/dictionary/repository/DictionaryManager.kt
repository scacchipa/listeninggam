package ar.com.westsoft.listening.dictionary.repository

import android.util.Log
import ar.com.westsoft.listening.dictionary.database.DictionaryDao
import ar.com.westsoft.listening.dictionary.database.StoredDefinition
import ar.com.westsoft.listening.dictionary.source.WiktionaryService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryManager @Inject constructor(
    private val wiktionaryService: WiktionaryService,
    private val dictionaryDao: DictionaryDao
) {
    suspend fun getDefinition(word: String): String {
        val lowercaseWord = word.lowercase().trim()

        // 1. Check local database first
        val cached = dictionaryDao.getDefinition(lowercaseWord)
        if (cached != null) {
            Log.d("DictionaryManager", "Found cached definition for $lowercaseWord")
            return cached.definition
        }

        // 2. If not found, fetch from API
        return try {
            Log.d("DictionaryManager", "Fetching definition for $lowercaseWord from API")
            val response = wiktionaryService.getDefinition(lowercaseWord)
            val englishItems = response["en"] ?: return "Word not found in English Wiktionary"

            val result = StringBuilder()
            englishItems.forEach { item ->
                val validDefinitions = item.definitions
                    .map { it.definition }
                    .filter { it.isNotBlank() }
                    .map {
                        // Keep HTML tags but normalize whitespace
                        it.replace(Regex("\\s+"), " ").trim()
                    }
                    .filter { it.isNotEmpty() }

                if (validDefinitions.isNotEmpty()) {
                    result.append("${item.partOfSpeech}:\n")
                    validDefinitions.forEachIndexed { index, def ->
                        result.append("  ${index + 1}. $def\n")
                    }
                    result.append("\n")
                }
            }

            if (result.isBlank()) {
                "No definitions found for \"$lowercaseWord\""
            } else {
                val definition = result.toString().trim()
                // 3. Save to local database
                dictionaryDao.insert(StoredDefinition(lowercaseWord, definition))
                definition
            }
        } catch (e: Exception) {
            Log.e("DictionaryManager", "Error fetching definition for $lowercaseWord", e)
            "Error fetching definition: ${e.localizedMessage}"
        }
    }
}
package ar.com.westsoft.listening.dictionary

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryManager @Inject constructor() {
    fun getDefinition(word: String): String {
        return "Definition of $word"
    }
}

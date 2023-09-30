package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object PreferencesKeys {
    val READ_WORD_AFTER_CURSOR = intPreferencesKey("read_word_after_cursor")
    val READ_WORD_BEFORE_CURSOR = intPreferencesKey("read_word_before_cursor")
    val SPEECH_RATE = doublePreferencesKey("speech_rate")
}

package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences

fun Preferences.getDictGameSettingsDSO() = DictGameSettingsDSO(
    readWordAfterCursor = this[PreferencesKeys.READ_WORD_AFTER_CURSOR] ?: 7,
    readWordBeforeCursor = this[PreferencesKeys.READ_WORD_BEFORE_CURSOR] ?: 2,
    speechRate = this[PreferencesKeys.SPEECH_RATE] ?: 100.0
)

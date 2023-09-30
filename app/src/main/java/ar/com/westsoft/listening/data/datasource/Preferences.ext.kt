package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences

fun Preferences.getDictGameSettingsDSO() = DictGameSettingsDSO(
    readWordAfterCursorValue = this[PreferencesKeys.READ_WORD_AFTER_CURSOR] ?: 7,
    readWordBeforeCursorValue = this[PreferencesKeys.READ_WORD_BEFORE_CURSOR] ?: 2
)

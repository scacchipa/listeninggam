package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences

fun Preferences.getDictGameSettingsDSO() = DictGameSettingsDSO(
    readWordAfterCursor = this[PreferencesKey.ReadWordAfterCursor],
    readWordBeforeCursor = this[PreferencesKey.ReadWordBeforeCursor],
    speechRate = this[PreferencesKey.SpeechRatePercentage],
    speedLevel = SpeedLevelPreference.fromKey(this[PreferencesKey.SpeedLevel]),
    columnPerPage = this[PreferencesKey.ColumnPerPage]
)

operator fun <T> Preferences.get(preferenceField: PreferencesKey<T>): T =
    this[preferenceField.key] ?: preferenceField.defaultValue

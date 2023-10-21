package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences
import ar.com.westsoft.listening.domain.dictationgame.settings.SpeedLevelPreference

fun Preferences.getDictGameSettingsDSO() = DictGameSettingsDSO(
    readWordAfterCursor = this[PreferencesKey.ReadWordAfterCursor],
    readWordBeforeCursor = this[PreferencesKey.ReadWordBeforeCursor],
    speechRate = this[PreferencesKey.SpeechRatePercentage],
    speedLevel = SpeedLevelPreference.fromKey(this[PreferencesKey.SpeedLevel])

)

operator fun <T> Preferences.get(preferenceField: PreferencesKey<T>): T =
    this[preferenceField.key] ?: preferenceField.defaultValue

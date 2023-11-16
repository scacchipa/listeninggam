package ar.com.westsoft.listening.screen.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordAfterCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordBeforeCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeechRatePercentage
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeedLevel
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.SpeedLevelPreference

data class DictGameSettings(
    val readWordAfterCursor: SettingsField<Int>,
    val readWordBeforeCursor: SettingsField<Int>,
    val speechRatePercentage: SettingsField<Float>,
    val speedLevel: SettingsField<SpeedLevelPreference>,
    val columnPerPage: SettingsField<Int>
) {
    fun <T> copy(param: PreferencesKey<T>, newValue: SettingsField<*>): DictGameSettings{

        @Suppress("UNCHECKED_CAST")
        return when(param) {
            ReadWordAfterCursor -> copy(readWordAfterCursor = newValue as SettingsField<Int>)
            ReadWordBeforeCursor -> copy(readWordBeforeCursor = newValue as SettingsField<Int>)
            SpeechRatePercentage -> copy(speechRatePercentage = newValue as SettingsField<Float>)
            SpeedLevel -> copy(speedLevel = newValue as SettingsField<SpeedLevelPreference>)
            PreferencesKey.ColumnPerPage -> copy(columnPerPage = newValue as SettingsField<Int>)
        }
    }
}

package ar.com.westsoft.listening.screen.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordAfterCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordBeforeCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeechRatePercentage
import ar.com.westsoft.listening.data.repository.SettingsField

data class DictGameSettings(
    val readWordAfterCursor: SettingsField,
    val readWordBeforeCursor: SettingsField,
    val speechRatePercentage: SettingsField
) {
    fun <T> copy(param: PreferencesKey<T>, newValue: SettingsField): DictGameSettings{
        return when(param) {
            ReadWordAfterCursor -> copy(readWordAfterCursor = newValue)
            ReadWordBeforeCursor -> copy(readWordBeforeCursor = newValue)
            SpeechRatePercentage -> copy(speechRatePercentage = newValue)
        }
    }
}


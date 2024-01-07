package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

object DictSettingsDataStoreUtils {
    fun createMockDefaultDataStore(
        mockPreferences: Preferences = createDefaultPreferences()
    ): DataStore<Preferences> {
        val mockDataStore = mock<DataStore<Preferences>>()

        `when`(mockDataStore.data).then {
            flow {
                emit(mockPreferences)
            }
        }

        return mockDataStore
    }

    fun createDefaultPreferences(): Preferences = preferencesOf(
        PreferencesKey.ReadWordAfterCursor.key to PreferencesKey.ReadWordAfterCursor.defaultValue,
        PreferencesKey.ReadWordBeforeCursor.key to PreferencesKey.ReadWordBeforeCursor.defaultValue,
        PreferencesKey.SpeechRatePercentage.key to PreferencesKey.SpeechRatePercentage.defaultValue,
        PreferencesKey.SpeedLevel.key to PreferencesKey.SpeedLevel.defaultValue,
        PreferencesKey.ColumnPerPage.key to PreferencesKey.ColumnPerPage.defaultValue
    )
}

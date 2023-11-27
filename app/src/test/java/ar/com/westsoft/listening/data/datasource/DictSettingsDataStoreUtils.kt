package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import ar.com.westsoft.listening.util.Constants
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

object DictSettingsDataStoreUtils {

    val DICT_SETTINGS_DATA_STORE_DEFAULT = DictGameSettingsDSO(
        readWordAfterCursor = Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
        readWordBeforeCursor = Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
        speechRate = Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
        speedLevel = Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT,
        columnPerPage = Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT
    )

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

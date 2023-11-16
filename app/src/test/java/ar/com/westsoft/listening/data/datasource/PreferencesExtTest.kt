package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import ar.com.westsoft.listening.util.Constants
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class PreferencesExtTest {

    private val mockPreferences = mock<Preferences>()
    private val mockDataStore = mock<DataStore<Preferences>>()

    private val readWordAfterCursorKey = PreferencesKey.ReadWordAfterCursor.key
    private val readWordBeforeCursorKey = PreferencesKey.ReadWordBeforeCursor.key
    private val speechRatePercentageKey = PreferencesKey.SpeechRatePercentage.key
    private val speedLevelKey = PreferencesKey.SpeedLevel.key
    private val columnPerPageKey = PreferencesKey.ColumnPerPage.key

    @Before
    fun setup() {
        
        `when`(mockDataStore.data).then {
            flow<Preferences> { emit(mockPreferences) }
        }
    }

    @Test
    fun getDictGameSettingsDSOFlowNormal() {

        `when`(mockPreferences[readWordAfterCursorKey]).thenReturn(PreferencesKey.ReadWordAfterCursor.defaultValue)
        `when`(mockPreferences[readWordBeforeCursorKey]).thenReturn(PreferencesKey.ReadWordBeforeCursor.defaultValue)
        `when`(mockPreferences[speechRatePercentageKey]).thenReturn(PreferencesKey.SpeechRatePercentage.defaultValue)
        `when`(mockPreferences[speedLevelKey]).thenReturn(PreferencesKey.SpeedLevel.defaultValue)
        `when`(mockPreferences[columnPerPageKey]).thenReturn(PreferencesKey.ColumnPerPage.defaultValue)

        val actualResult = mockPreferences.getDictGameSettingsDSO()

        val expectResult = DictGameSettingsDSO(
            readWordAfterCursor = Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
            readWordBeforeCursor = Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
            speechRate = Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
            speedLevel = Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT,
            columnPerPage = Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT
        )

        assert(actualResult == expectResult)
    }

    @Test
    fun getDictGameSettingsDSOFlowNormal_does_not_speedLevel() {
        `when`(mockPreferences[speedLevelKey]).thenReturn("Wrong name")
        `when`(mockPreferences[readWordAfterCursorKey]).thenReturn(null)
        `when`(mockPreferences[readWordBeforeCursorKey]).thenReturn(null)
        `when`(mockPreferences[speechRatePercentageKey]).thenReturn(null)
        `when`(mockPreferences[speedLevelKey]).thenReturn(null)
        `when`(mockPreferences[columnPerPageKey]).thenReturn(null)

        val actualResult = mockPreferences.getDictGameSettingsDSO()

        val expectResult = DictGameSettingsDSO(
            readWordAfterCursor = Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
            readWordBeforeCursor = Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
            speechRate = Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
            speedLevel = Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT,
            columnPerPage = Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT
        )

        assert(actualResult == expectResult)
    }
}

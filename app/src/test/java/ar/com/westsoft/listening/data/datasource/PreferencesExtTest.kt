package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ar.com.westsoft.listening.util.Constants
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class PreferencesExtTest {

    private val mockPreferences = mock<Preferences>()
    private val mockDataStore = mock<DataStore<Preferences>>()

    private val readWordAfterCursorKey =
        intPreferencesKey(Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR)
    private val readWordBeforeCursorKey =
        intPreferencesKey(Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR)
    private val speechRatePercentageKey =
        floatPreferencesKey(Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE)
    private val speedLevelKey = stringPreferencesKey(Constants.PREFERENCES_KEY_SPEED_LEVEL)
    private val columnPerPageKey = intPreferencesKey(Constants.PREFERENCES_KEY_COLUMN_PER_PAGE)

    private val readWordAfterCursorValue = Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT
    private val readWordBeforeCursorValue =
        Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT
    private val speechRatePercentageValue = Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT
    private val speedLevelValue = Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT.name
    private val columnPerPageValue = Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT

    @Before
    fun setup() {
        `when`(mockPreferences[readWordAfterCursorKey]).thenReturn(readWordAfterCursorValue)
        `when`(mockPreferences[readWordBeforeCursorKey]).thenReturn(readWordBeforeCursorValue)
        `when`(mockPreferences[speechRatePercentageKey]).thenReturn(speechRatePercentageValue)
        `when`(mockPreferences[speedLevelKey]).thenReturn(speedLevelValue)
        `when`(mockPreferences[columnPerPageKey]).thenReturn(columnPerPageValue)

        `when`(mockDataStore.data).then {
            flow<Preferences> { emit(mockPreferences) }
        }
    }

    @Test
    fun getDictGameSettingsDSOFlow() {

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

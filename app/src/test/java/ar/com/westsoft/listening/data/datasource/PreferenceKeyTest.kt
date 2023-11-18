package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.util.Constants
import org.junit.Test

class PreferenceKeyTest {

    @Test
    fun readWordAfterCursor_Test() {
        val subject = PreferencesKey.ReadWordAfterCursor

        assert(subject.key == intPreferencesKey(Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR))
        assert(subject.defaultValue == Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT)
        assert(subject.convert("100") == 100)
        assert(subject.convert("-100") == -100)
        assert(subject.convert("0") == 0)
        assert(!subject.conditionToSave(0))
        assert(subject.conditionToSave(1))
        assert(subject.conditionToSave(100))
        assert(subject.conditionToSave(200))
        assert(!subject.conditionToSave(201))
    }

    @Test
    fun readWordBeforeCursor_test() {
        val subject = PreferencesKey.ReadWordBeforeCursor

        assert(subject.key == intPreferencesKey(Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR))
        assert(subject.defaultValue == Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT)
        assert(subject.convert("100") == 100)
        assert(subject.convert("-100") == -100)
        assert(subject.convert("0") == 0)
        assert(!subject.conditionToSave(-1))
        assert(subject.conditionToSave(0))
        assert(subject.conditionToSave(50))
        assert(subject.conditionToSave(100))
        assert(!subject.conditionToSave(101))
        assert(!subject.conditionToSave(null))
    }

    @Test
    fun speechRatePercentage_test() {
        val subject = PreferencesKey.SpeechRatePercentage

        assert(subject.key == floatPreferencesKey(Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE))
        assert(subject.defaultValue == Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT)
        assert(subject.convert("100") == 100f)
        assert(subject.convert("100.0") == 100f)
        assert(subject.convert("100.5") == 100.5f)
        assert(subject.convert("-100") == -100f)
        assert(subject.convert("-100.0") == -100f)
        assert(subject.convert("0") == 0f)
        assert(subject.convert("0.0") == 0f)
        assert(!subject.conditionToSave(24.9f))
        assert(subject.conditionToSave(25f))
        assert(subject.conditionToSave(100f))
        assert(subject.conditionToSave(400f))
        assert(!subject.conditionToSave(400.1f))
        assert(!subject.conditionToSave(null))
        assert(!subject.conditionToSave(Float.NaN))
    }

    @Test
    fun speedLevel_test() {
        val subject = PreferencesKey.SpeedLevel

        assert(subject.key == stringPreferencesKey(Constants.PREFERENCES_KEY_SPEED_LEVEL))
        assert(subject.defaultValue == Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT.name)
        assert(subject.convert(SpeedLevelPreference.MEDIUM_SPEED_LEVEL.name)
                == SpeedLevelPreference.MEDIUM_SPEED_LEVEL.name)
        assert(subject.convert("") == SpeedLevelPreference.NORMAL_SPEED_LEVEL.name)
        assert(subject.conditionToSave(""))
        assert(subject.conditionToSave(null))
    }

    @Test
    fun columnPerPage_test() {
        val subject = PreferencesKey.ColumnPerPage

        assert(subject.key == intPreferencesKey(Constants.PREFERENCES_KEY_COLUMN_PER_PAGE))
        assert(subject.defaultValue == Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT)
        assert(subject.convert("100") == 100)
        assert(subject.convert("-100") == -100)
        assert(subject.convert("0") == 0)
        assert(subject.conditionToSave(15))
        assert(subject.conditionToSave(100))
        assert(subject.conditionToSave(200))
        assert(!subject.conditionToSave(14))
        assert(!subject.conditionToSave(201))
        assert(!subject.conditionToSave(null))
    }
}

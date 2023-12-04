package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ar.com.westsoft.listening.util.Constants
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_SPEED_LEVEL
import ar.com.westsoft.listening.util.Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT

sealed class PreferencesKey<T>(
    val key: Preferences.Key<T>,
    val defaultValue: T,
    val convert: (String) -> T?,
    val conditionToSave: (T?) -> Boolean
) {
    object ReadWordAfterCursor
        : PreferencesKey<Int>(
        key = intPreferencesKey(PREFERENCES_KEY_READ_WORD_AFTER_CURSOR),
        defaultValue = PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
        convert = { it.trim().toIntOrNull() },
        conditionToSave = { it in 1..200 }
    )

    object ReadWordBeforeCursor
        : PreferencesKey<Int>(
        key = intPreferencesKey(PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR),
        defaultValue = PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
        convert = { it.trim().toIntOrNull() },
        conditionToSave = { it in 0..100 }
    )

    object SpeechRatePercentage
        : PreferencesKey<Float>(
        key = floatPreferencesKey(PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE),
        defaultValue = PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
        convert = { it.trim().toFloatOrNull() },
        conditionToSave = { (it ?: Float.NaN) in 25.0..400.0 }
    )

    object SpeedLevel : PreferencesKey<String>(
        key = stringPreferencesKey(PREFERENCES_KEY_SPEED_LEVEL),
        defaultValue = PREFERENCES_KEY_SPEED_LEVEL_DEFAULT.name,
        convert = { SpeedLevelPreference.fromKey(it).name },
        conditionToSave = { true }
    )

    object ColumnPerPage : PreferencesKey<Int>(
        key = intPreferencesKey(Constants.PREFERENCES_KEY_COLUMN_PER_PAGE),
        defaultValue = PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT,
        convert = { it.trim().toIntOrNull() },
        conditionToSave = { it in 15..200 }
    )
}

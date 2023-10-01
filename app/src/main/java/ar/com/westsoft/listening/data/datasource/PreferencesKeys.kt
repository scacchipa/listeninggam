package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import ar.com.westsoft.listening.util.Constants

sealed class PreferencesKey<T>(
    val key: Preferences.Key<T>,
    val defaultValue: T,
    val convert: (String) -> T?,
    val conditionToSave: (T?) -> Boolean
) {
    object ReadWordAfterCursor
        : PreferencesKey<Int>(
        key = intPreferencesKey("read_word_after_cursor"),
        defaultValue = Constants.READ_WORD_AFTER_CURSOR_DEFAULT,
        convert = { it.toIntOrNull() },
        conditionToSave = { it in 1..200 }
    )

    object ReadWordBeforeCursor
        : PreferencesKey<Int>(
        key = intPreferencesKey("read_word_before_cursor"),
        defaultValue = Constants.READ_WORD_BEFORE_CURSOR_DEFAULT,
        convert = { it.toIntOrNull() },
        conditionToSave = { it in 1..100 }
    )

    object SpeechRatePercentage
        : PreferencesKey<Double>(
        key = doublePreferencesKey("speech_rate_percentage"),
        defaultValue = Constants.SPEECH_RATE_PERCENTAGE_DEFAULT,
        convert = { it.toDoubleOrNull() },
        conditionToSave = { (it ?: Double.NaN) in 25.0..400.0 }
    )
}

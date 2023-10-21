package ar.com.westsoft.listening.data.datasource

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import ar.com.westsoft.listening.domain.dictationgame.settings.SpeedLevelPreference
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
        : PreferencesKey<Float>(
        key = floatPreferencesKey("speech_rate_percentage"),
        defaultValue = Constants.SPEECH_RATE_PERCENTAGE_DEFAULT,
        convert = { it.toFloatOrNull() },
        conditionToSave = { (it ?: Float.NaN) in 25.0..400.0 }
    )

    object SpeedLevel : PreferencesKey<String>(
        key = stringPreferencesKey("speed_level"),
        defaultValue = Constants.SPEED_LEVEL_DEFAULT.name,
        convert = { SpeedLevelPreference.fromKey(it).name },
        conditionToSave = { true }
    )
}

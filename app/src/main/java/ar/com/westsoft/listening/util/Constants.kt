package ar.com.westsoft.listening.util

import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.datasource.DictGameSettingsDSO

object Constants {
    const val PREFERENCES_KEY_READ_WORD_AFTER_CURSOR = "read_word_after_cursor"
    const val PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR = "read_word_before_cursor"
    const val PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE = "speech_rate_percentage"
    const val PREFERENCES_KEY_SPEED_LEVEL = "speed_level"
    const val PREFERENCES_KEY_COLUMN_PER_PAGE = "column_per_page"

    const val PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT = 7
    const val PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT = 2
    const val PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT = 100f
    val PREFERENCES_KEY_SPEED_LEVEL_DEFAULT = SpeedLevelPreference.NORMAL_SPEED_LEVEL
    const val PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT = 40

    val DICT_SETTINGS_DATA_STORE_DEFAULT = DictGameSettingsDSO(
        readWordAfterCursor = PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
        readWordBeforeCursor = PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
        speechRate = PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
        speedLevel = PREFERENCES_KEY_SPEED_LEVEL_DEFAULT,
        columnPerPage = PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT
    )
}

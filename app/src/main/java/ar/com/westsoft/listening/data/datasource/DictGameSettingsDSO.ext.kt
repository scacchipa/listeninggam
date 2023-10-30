package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings

fun DictGameSettingsDSO.toSetting() =
    DictGameSettings(
        readWordAfterCursor = SettingsField(readWordAfterCursor, true),
        readWordBeforeCursor = SettingsField(readWordBeforeCursor, true),
        speechRatePercentage = SettingsField(speechRate, true),
        speedLevel = SettingsField(speedLevel, true),
        columnPerPage = SettingsField(columnPerPage, true)
    )

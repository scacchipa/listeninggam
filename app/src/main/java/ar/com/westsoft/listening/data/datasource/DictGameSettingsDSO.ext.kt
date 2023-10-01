package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings

fun DictGameSettingsDSO.toSetting() =
    DictGameSettings(
        readWordAfterCursor = SettingsField(readWordAfterCursor.toString(), true),
        readWordBeforeCursor = SettingsField(readWordBeforeCursor.toString(), true),
        speechRatePercentage = SettingsField(speechRate.toString(), true)
    )

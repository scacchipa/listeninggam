package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameSettings

fun DictGameSettingsDSO.toSetting() =
    DictGameSettings(
        readWordAfterCursor = SettingsField(readWordAfterCursorValue.toString(), true),
        readWordBeforeCursor = SettingsField(readWordBeforeCursorValue.toString(), true)
    )

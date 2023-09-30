package ar.com.westsoft.listening.screen.dictationgame

import ar.com.westsoft.listening.data.repository.SettingsField

fun DictGameSettingsDSO.toSetting() =
    DictGameSettings(
        readWordAfterCursor = SettingsField(readWordAfterCursorValue.toString(), true),
        readWordBeforeCursor = SettingsField(readWordBeforeCursorValue.toString(), true)
    )

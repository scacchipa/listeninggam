package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.SettingsField

data class DictGameSetting(
    val readWordAfterCursor: SettingsField<String>,
    val readWordBeforeCursor: SettingsField<String>
)


data class DictGameSettingsDSO(
    val readWordAfterCursorValue: Int,
    val readWordBeforeCursorValue: Int
)
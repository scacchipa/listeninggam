package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.SettingsField

data class DictGameSettings(
    val readWordAfterCursor: SettingsField<String>,
    val readWordBeforeCursor: SettingsField<String>
)




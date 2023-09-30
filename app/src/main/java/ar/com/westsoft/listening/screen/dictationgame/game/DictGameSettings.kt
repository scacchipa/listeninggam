package ar.com.westsoft.listening.screen.dictationgame.game

import ar.com.westsoft.listening.data.repository.SettingsField

data class DictGameSettings(
    val readWordAfterCursor: SettingsField<String>,
    val readWordBeforeCursor: SettingsField<String>
)




package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue

data class DictGameScreenSettingsState(
    val readWordAfterCursor: TextFieldValue,
    val readWordBeforeCursor: TextFieldValue,
    val speechRate: TextFieldValue
)

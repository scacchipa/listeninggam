package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.input.TextFieldValue
import ar.com.westsoft.listening.data.repository.toAnnotatedString
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameScreenSettingsState

fun DictGameSettings.toScreenSettingsState() =
    DictGameScreenSettingsState(
        readWordAfterCursor = TextFieldValue(readWordAfterCursor.toAnnotatedString()),
        readWordBeforeCursor = TextFieldValue(readWordBeforeCursor.toAnnotatedString())
    )

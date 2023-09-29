package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.text.input.TextFieldValue
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.toAnnotatedString

fun DictGameSettings.toScreenSettingsState() =
    DictGameScreenSettingsState(
        readWordAfterCursor = TextFieldValue(readWordAfterCursor.toAnnotatedString()),
        readWordBeforeCursor = TextFieldValue(readWordBeforeCursor.toAnnotatedString())
    )

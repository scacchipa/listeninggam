package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue
import ar.com.westsoft.listening.data.repository.toAnnotatedString

fun DictGameSettings.toScreenSettingsState() =
    DictGameScreenSettingsState(
        readWordAfterCursor = TextFieldValue(readWordAfterCursor.toAnnotatedString()),
        readWordBeforeCursor = TextFieldValue(readWordBeforeCursor.toAnnotatedString()),
        speechRate = TextFieldValue(speechRate.toAnnotatedString())
    )

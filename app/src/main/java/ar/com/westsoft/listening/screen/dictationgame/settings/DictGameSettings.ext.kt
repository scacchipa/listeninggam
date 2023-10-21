package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue

fun DictGameSettings.toScreenSettingsState() =
    DictGameScreenSettingsState(
        readWordAfterCursor = TextFieldValue(readWordAfterCursor.value.toString()),
        readWordBeforeCursor = TextFieldValue(readWordBeforeCursor.toString()),
        speechRate = TextFieldValue(speechRatePercentage.value.toString()),
        speedLevelField = this.speedLevel
    )

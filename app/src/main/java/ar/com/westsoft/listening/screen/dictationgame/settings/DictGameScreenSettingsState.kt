package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.repository.SettingsField

data class DictGameScreenSettingsState(
    val readWordAfterCursor: TextFieldValue,
    val readWordBeforeCursor: TextFieldValue,
    val speechRate: TextFieldValue,
    val speedLevelField: SettingsField<SpeedLevelPreference>,
    val columnPerPage: TextFieldValue
)

package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.settings.SelectableButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictGameSettingScreen(
    onBack: () -> Unit
) {
    val settingsViewModel = hiltViewModel<DictGameSettingsViewModel>()

    val settingsState = settingsViewModel.screenStateFlow.collectAsState()

    Column {
        Text(
            text = "Options:",
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Read:",
            style = MaterialTheme.typography.bodyLarge
        )

        Row {
            Text(
                "- after the cursor ",
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = settingsState.value.readWordAfterCursor,
                onValueChange = {
                    settingsViewModel.setReadWordAfterCursor(it)
                },
                modifier = Modifier.width(100.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )

            Text(
                text = " word.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row {
            Text(
                "- before the cursor ",
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = settingsState.value.readWordBeforeCursor,
                onValueChange = {
                    settingsViewModel.setReadWordBeforeCursor(it)
                },
                modifier = Modifier.width(100.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )

            Text(
                text = " word.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row {
            Text(
                "- speech rate ",
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = settingsState.value.speechRate,
                onValueChange = {
                    settingsViewModel.setSpeechRate(it)
                },
                modifier = Modifier.width(100.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )

            Text(
                text = "%.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row {
            Text("Speed Level:")
            Column {
                val speedLevel = settingsState.value.speedLevelField
                SelectableButton(
                    settingField = speedLevel,
                    onSelected = {
                        settingsViewModel.setSpeedLevel(SpeedLevelPreference.LOW_SPEED_LEVEL)
                    },
                    value = SpeedLevelPreference.LOW_SPEED_LEVEL,
                    text = "LOW (50%)",
                    textColor = Color.Green
                )

                SelectableButton(
                    settingField = speedLevel,
                    onSelected = {
                        settingsViewModel.setSpeedLevel(SpeedLevelPreference.MEDIUM_SPEED_LEVEL)
                    },
                    value = SpeedLevelPreference.MEDIUM_SPEED_LEVEL,
                    text = "MEDIUM (75%)",
                    textColor = Color.Yellow
                )
                SelectableButton(
                    settingField = speedLevel,
                    onSelected = {
                        settingsViewModel.setSpeedLevel(SpeedLevelPreference.NORMAL_SPEED_LEVEL)
                    },
                    value = SpeedLevelPreference.NORMAL_SPEED_LEVEL,
                    text = "NORMAL (100%)",
                    textColor = Color.Magenta
                )
                SelectableButton(
                    settingField = speedLevel,
                    onSelected = {
                        settingsViewModel.setSpeedLevel(SpeedLevelPreference.HIGH_SPEED_LEVEL)
                    },
                    value = SpeedLevelPreference.HIGH_SPEED_LEVEL,
                    text = "HIGH (125%)",
                    textColor = Color.Red
                )
            }
        }

        Row {
            Text(
                "Column per page: ",
                style = MaterialTheme.typography.bodyMedium
            )

            TextField(
                value = settingsState.value.columnPerPage,
                onValueChange = {
                    settingsViewModel.setColumnPerPage(it)
                },
                modifier = Modifier.width(100.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )

            Text(
                text = "column.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(onClick = {
            onBack()
        }) {
            Text(
                text = "Close",
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

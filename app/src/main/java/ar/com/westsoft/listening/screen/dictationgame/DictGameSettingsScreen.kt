package ar.com.westsoft.listening.screen.dictationgame

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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

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

data class DictGameScreenSettingsState(
    val readWordAfterCursor: TextFieldValue = TextFieldValue(),
    val readWordBeforeCursor: TextFieldValue = TextFieldValue()
)

package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference

@Composable
fun SelectableButton(
    settingField: SettingsField<SpeedLevelPreference>,
    onSelected: () -> Unit,
    value: SpeedLevelPreference,
    text: String,
    textColor: Color
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = if (settingField.value == value)
                Color.Blue else Color.LightGray
        ),
        onClick = onSelected,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            style = MaterialTheme.typography.bodyMedium,
            color = if (settingField.wasSaved) textColor else Color.Red,
            text = text
        )
    }
}

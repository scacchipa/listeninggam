package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DictPreferencesScreen(
    onBack: () -> Unit
) {
    Column {
        Text(
            text = "Options:",
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

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

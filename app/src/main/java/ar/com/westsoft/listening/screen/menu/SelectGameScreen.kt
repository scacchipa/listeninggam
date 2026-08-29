package ar.com.westsoft.listening.screen.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    widthDp = 400,
    heightDp = 600
)
@Composable
fun SelectGameScreen(
    navDictationGame: () -> Unit = { }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(0.5f),
            thickness = DividerDefaults.Thickness,
            color = Color.Transparent
        )
        MenuButton(
            modifier = Modifier.weight(1f),
            text = "Dictation",
            action = navDictationGame
        )
        HorizontalDivider(
            modifier = Modifier.weight(0.5f),
            thickness = DividerDefaults.Thickness,
            color = Color.Transparent
        )
    }
}

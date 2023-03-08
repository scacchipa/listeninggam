package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ar.com.westsoft.listening.screen.MenuButton

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
        Divider(
            modifier = Modifier.weight(0.5f),
            color = Color.Transparent
        )
        MenuButton(
            modifier = Modifier.weight(1f),
            text = "Dictation",
            action = navDictationGame
        )
        Divider(
            modifier = Modifier.weight(0.5f),
            color = Color.Transparent
        )
    }
}
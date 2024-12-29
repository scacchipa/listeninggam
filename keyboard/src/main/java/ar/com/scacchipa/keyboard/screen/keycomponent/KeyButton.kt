package ar.com.scacchipa.keyboard.screen.keycomponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    key: Key,
    text: String,
    height: Dp,
    width: Dp = height,
    action: (KeyEvent) -> Unit = { },
) {
    OutlinedButton(
        onClick = {
            action(
                KeyEvent(
                    nativeKeyEvent = android.view.KeyEvent(
                        NativeKeyEvent.ACTION_DOWN, key.nativeKeyCode
                    )
                )
            )
        },
        modifier = modifier
            .size(width = width, height = height)
            .padding(height / 10),
        shape = RoundedCornerShape(10),
        border = BorderStroke(height / 50, color = Color.DarkGray),
        contentPadding = PaddingValues(top = 0.dp)
    ) {
        Text(
            modifier = modifier
                .background(color = Color.Transparent)
                .padding(0.dp),
            color = Color.DarkGray,
            text = text,
            textAlign = TextAlign.Center,
            fontSize = height.value.sp / 1.6f
        )
    }
}
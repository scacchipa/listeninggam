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

import ar.com.scacchipa.keyboard.screen.LocalKeyboardOnKeyAction
import ar.com.scacchipa.keyboard.screen.LocalKeyboardPressedKeys

@Composable
fun KeyButton(
    modifier: Modifier = Modifier,
    key: Key,
    text: String,
    height: Dp,
    width: Dp = height,
    action: (KeyEvent) -> Unit = { },
) {
    val pressedKeys = LocalKeyboardPressedKeys.current
    val onKeyAction = LocalKeyboardOnKeyAction.current
    val isMarked = pressedKeys.contains(key)

    OutlinedButton(
        onClick = {
            onKeyAction(key)
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
        border = BorderStroke(
            height / 50,
            color = if (isMarked) Color.Red else Color.DarkGray
        ),
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            containerColor = if (isMarked) Color(0xFFFFEBEE) else Color.Transparent
        ),
        contentPadding = PaddingValues(top = 0.dp)
    ) {
        Text(
            modifier = modifier
                .background(color = Color.Transparent)
                .padding(0.dp),
            color = if (isMarked) Color.Red else Color.DarkGray,
            text = text,
            textAlign = TextAlign.Center,
            fontSize = height.value.sp / 1.6f
        )
    }
}

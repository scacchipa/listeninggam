package ar.com.scacchipa.keyboard.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import ar.com.scacchipa.keyboard.screen.simplekeyboard.BigKeyKeyboard
import ar.com.scacchipa.keyboard.screen.standardkeyboard.StandardKeyboard

@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    widthDp: Dp,
    action: (KeyEvent) -> Unit,
    keyboardType: KeyboardType = KeyboardType.BigKey,
    resetToken: Any? = null
) {
    val pressedKeys = remember { mutableStateOf(setOf<Key>()) }

    LaunchedEffect(resetToken) {
        pressedKeys.value = emptySet()
    }

    CompositionLocalProvider(
        LocalKeyboardPressedKeys provides pressedKeys.value,
        LocalKeyboardOnKeyAction provides { key ->
            pressedKeys.value = pressedKeys.value + key
        }
    ) {
        when (keyboardType) {
            KeyboardType.BigKey -> BigKeyKeyboard(modifier, widthDp, action)
            KeyboardType.Standard -> StandardKeyboard(widthDp, modifier, action)
        }
    }
}

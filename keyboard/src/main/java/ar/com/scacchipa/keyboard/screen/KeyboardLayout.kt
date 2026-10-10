package ar.com.scacchipa.keyboard.screen

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.Dp
import ar.com.scacchipa.keyboard.screen.simplekeyboard.BigKeyKeyboard
import ar.com.scacchipa.keyboard.screen.standardkeyboard.StandardKeyboard

@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    widthDp: Dp,
    action: (KeyEvent) -> Unit,
    onBack: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.BigKey,
    resetToken: Any? = null
) {
    val pressedKeys = remember { mutableStateOf(setOf<Key>()) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(resetToken) {
        pressedKeys.value = emptySet()
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CompositionLocalProvider(
        LocalKeyboardPressedKeys provides pressedKeys.value,
        LocalKeyboardOnKeyAction provides { key ->
            pressedKeys.value += key
        }
    ) {
        Box(
            modifier = modifier
                .focusRequester(focusRequester)
                .focusable()
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.Back && keyEvent.type == KeyEventType.KeyDown) {
                        onBack()
                        return@onKeyEvent true
                    }
                    if (keyEvent.type == KeyEventType.KeyDown) {
                        pressedKeys.value += keyEvent.key
                    }
                    action(keyEvent)
                    true
                }
        ) {
            when (keyboardType) {
                KeyboardType.BigKey -> BigKeyKeyboard(Modifier, widthDp, action)
                KeyboardType.Standard -> StandardKeyboard(Modifier, widthDp, action)
            }
        }
    }
}

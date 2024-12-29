package ar.com.scacchipa.keyboard.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import ar.com.scacchipa.keyboard.screen.simplekeyboard.BigKeyKeyboard
import ar.com.scacchipa.keyboard.screen.standardkeyboard.StandardKeyboard

@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    widthDp: Dp,
    action: (KeyEvent) -> Unit,
    keyboardType: KeyboardType = KeyboardType.BigKey
) =
    when (keyboardType) {
        KeyboardType.BigKey -> BigKeyKeyboard(modifier, widthDp, action)
        KeyboardType.Standard -> StandardKeyboard(widthDp, modifier, action)
    }

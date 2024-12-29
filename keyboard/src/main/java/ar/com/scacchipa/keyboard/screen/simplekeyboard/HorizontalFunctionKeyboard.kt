package ar.com.scacchipa.keyboard.screen.simplekeyboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import ar.com.scacchipa.keyboard.screen.keycomponent.InvisibleKeyButton
import ar.com.scacchipa.keyboard.screen.keycomponent.KeyButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HorizontalFunctionKeyboard(
    sideKey: Dp,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    action: (KeyEvent) -> Unit,
) {
    Row(
        modifier = Modifier.offset(offset.x, offset.y
        )
    ) {
        KeyButton(
            key = Key.Spacebar, text = "Rd", height = sideKey, width = sideKey * 2, action = action
        )
        KeyButton(
            key = Key.Apostrophe, text = "Lt", height = sideKey, width = sideKey * 2, action = action
        )
        KeyButton(
            key = Key.Backslash, text = "Wd", height = sideKey, width = sideKey * 2, action = action
        )
        KeyButton(
            key = Key.Equals, text = "Ph", height = sideKey, width = sideKey * 2, action = action
        )
        InvisibleKeyButton(height = sideKey, width = sideKey * 3)
    }
}

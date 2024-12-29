package ar.com.scacchipa.keyboard.screen.standardkeyboard

import androidx.compose.foundation.layout.Column
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
import ar.com.scacchipa.keyboard.screen.keycomponent.KeyButton
import ar.com.scacchipa.keyboard.screen.keycomponent.NullKeyButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArrowsKeyBoard(
    sideKey: Dp,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    action: (KeyEvent) -> Unit,
) {
    Column(
        modifier = Modifier.offset(
            x = offset.x,
            y = offset.y
        )
    ) {
        Row {
            NullKeyButton(height = sideKey)
            KeyButton(key = Key.DirectionUp, text = "↑", height = sideKey, action = action)
            NullKeyButton(height = sideKey)
        }
        Row {
            KeyButton(key = Key.DirectionLeft, text = "←", height = sideKey, action = action)
            KeyButton(key = Key.DirectionDown, text = "↓", height = sideKey, action = action)
            KeyButton(key = Key.DirectionRight, text = "→", height = sideKey, action = action)
        }
    }
}
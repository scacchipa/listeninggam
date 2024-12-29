package ar.com.scacchipa.keyboard.screen.simplekeyboard

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
import ar.com.scacchipa.keyboard.screen.keycomponent.InvisibleKeyButton
import ar.com.scacchipa.keyboard.screen.keycomponent.KeyButton

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleQwertyKeyboard(
    sideKey: Dp,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    action: (KeyEvent) -> Unit,
) {
    Column(
        modifier = Modifier.offset(offset.x, offset.y)
    ) {
        Row {
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
            KeyButton(key = Key.One, text = "1", height = sideKey, action = action)
            KeyButton(key = Key.Two, text = "2", height = sideKey, action = action)
            KeyButton(key = Key.Three, text = "3", height = sideKey, action = action)
            KeyButton(key = Key.Four, text = "4", height = sideKey, action = action)
            KeyButton(key = Key.Five, text = "5", height = sideKey, action = action)
            KeyButton(key = Key.Six, text = "6", height = sideKey, action = action)
            KeyButton(key = Key.Seven, text = "7", height = sideKey, action = action)
            KeyButton(key = Key.Eight, text = "8", height = sideKey, action = action)
            KeyButton(key = Key.Nine, text = "9", height = sideKey, action = action)
            KeyButton(key = Key.Zero, text = "0", height = sideKey, action = action)
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)

        }
        Row {
//            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
            KeyButton(key = Key.Q, text = "Q", height = sideKey, action = action)
            KeyButton(key = Key.W, text = "W", height = sideKey, action = action)
            KeyButton(key = Key.E, text = "E", height = sideKey, action = action)
            KeyButton(key = Key.R, text = "R", height = sideKey, action = action)
            KeyButton(key = Key.T, text = "T", height = sideKey, action = action)
            KeyButton(key = Key.Y, text = "Y", height = sideKey, action = action)
            KeyButton(key = Key.U, text = "U", height = sideKey, action = action)
            KeyButton(key = Key.I, text = "I", height = sideKey, action = action)
            KeyButton(key = Key.O, text = "O", height = sideKey, action = action)
            KeyButton(key = Key.P, text = "P", height = sideKey, action = action)
            KeyButton(key = Key.DirectionUp, text = "↑", height = sideKey, action = action)
        }
        Row {
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
            KeyButton(key = Key.A, text = "A", height = sideKey, action = action)
            KeyButton(key = Key.S, text = "S", height = sideKey, action = action)
            KeyButton(key = Key.D, text = "D", height = sideKey, action = action)
            KeyButton(key = Key.F, text = "F", height = sideKey, action = action)
            KeyButton(key = Key.G, text = "G", height = sideKey, action = action)
            KeyButton(key = Key.H, text = "H", height = sideKey, action = action)
            KeyButton(key = Key.J, text = "J", height = sideKey, action = action)
            KeyButton(key = Key.K, text = "K", height = sideKey, action = action)
            KeyButton(key = Key.L, text = "L", height = sideKey, action = action)
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
            KeyButton(key = Key.DirectionDown, text = "↓", height = sideKey, action = action)
        }
        Row {
            KeyButton(
                key = Key.Spacebar, text = "_", height = sideKey, width = sideKey, action = action
            )
            KeyButton(key = Key.Z, text = "Z", height = sideKey, action = action)
            KeyButton(key = Key.X, text = "X", height = sideKey, action = action)
            KeyButton(key = Key.C, text = "C", height = sideKey, action = action)
            KeyButton(key = Key.V, text = "V", height = sideKey, action = action)
            KeyButton(key = Key.B, text = "B", height = sideKey, action = action)
            KeyButton(key = Key.N, text = "N", height = sideKey, action = action)
            KeyButton(key = Key.M, text = "M", height = sideKey, action = action)
            KeyButton(
                key = Key.Spacebar, text = "_", height = sideKey, width = sideKey, action = action
            )
            KeyButton(key = Key.DirectionLeft, text = "←", height = sideKey, action = action)
            KeyButton(key = Key.DirectionRight, text = "→", height = sideKey, action = action)

        }
    }
}

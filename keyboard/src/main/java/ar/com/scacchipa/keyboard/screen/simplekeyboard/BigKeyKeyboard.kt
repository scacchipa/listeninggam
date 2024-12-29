package ar.com.scacchipa.keyboard.screen.simplekeyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun BigKeyKeyboard(
    modifier: Modifier = Modifier,
    widthDp: Dp,
    action: (KeyEvent) -> Unit
) {
    val sideKey = widthDp / 11

    Box(
        modifier = modifier
            .offset(0.dp, 0.dp)
            .size(widthDp, sideKey * 5),
    ) {
        SimpleQwertyKeyboard(
            sideKey = sideKey,
            offset = DpOffset(0.dp, sideKey),
            action = action,
        )
        HorizontalFunctionKeyboard(
            sideKey = sideKey,
            offset = DpOffset(0.dp, 0.dp),
            action = action
        )
    }
}
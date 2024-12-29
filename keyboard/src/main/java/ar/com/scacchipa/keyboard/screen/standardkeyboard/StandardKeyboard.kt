package ar.com.scacchipa.keyboard.screen.standardkeyboard

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
fun StandardKeyboard(
    widthDp: Dp,
    modifier: Modifier = Modifier,
    action: (KeyEvent) -> Unit
) {
    val sideKey = widthDp / 12.5f

    Box(
        modifier = modifier
            .offset(0.dp, 0.dp)
            .size(widthDp, sideKey * 5),
    ) {
        QwertyKeyBoard(
            sideKey = sideKey,
            action = action,
        )
        VerticalFunctionKeyBoard(
            sideKey = sideKey,
            offset = DpOffset(sideKey * 10.5f, 0.dp),
            action = action
        )
        ArrowsKeyBoard(
            sideKey = sideKey,
            offset = DpOffset(sideKey * 8.5f, sideKey * 3f),
            action = action
        )
    }
}

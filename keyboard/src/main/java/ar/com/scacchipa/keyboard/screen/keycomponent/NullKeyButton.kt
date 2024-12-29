package ar.com.scacchipa.keyboard.screen.keycomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun NullKeyButton(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp = height
) {
    Box(
        modifier = modifier
            .size(width = width, height = height)
    )
}

package ar.com.scacchipa.keyboard.screen.keycomponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun InvisibleKeyButton(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp = height
) {
    OutlinedButton(
        modifier = modifier
            .size(width = width, height = height)
            .padding(height / 10),
        shape = RoundedCornerShape(10),
        border = BorderStroke(height / 50, color = Color.LightGray),
        onClick = { }
    ) { }
}
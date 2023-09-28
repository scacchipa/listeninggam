package ar.com.westsoft.listening.screen.menu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ar.com.westsoft.listening.R

@Preview(
    widthDp = 400,
    heightDp = 200
)
@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String = "Texto prueba",
    @DrawableRes drawableId: Int = R.drawable.ic_launcher_foreground,
    action: () -> Unit = { }
) {
    RoundedFrame(
        modifier = modifier.fillMaxWidth(0.8f),
        action = action
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier
                    .fillMaxHeight(0.7f)
                    .weight(1f),
                painter = painterResource(id = drawableId),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Text(
                modifier = modifier.weight(1f),
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

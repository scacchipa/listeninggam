package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.com.westsoft.listening.screen.RoundedFrame

@Preview(
    widthDp = 400,
    heightDp = 600
)
@Composable
fun AdviceScreen(advice: String = "Advice ...") {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier.weight(0.5f),
            color = Color.Transparent
        )
        RoundedFrame(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = advice,
                fontSize = 30.sp,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Divider(
            modifier = Modifier.weight(0.5f),
            color = Color.Transparent
        )
    }
}
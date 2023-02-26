package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    widthDp = 400,
    heightDp = 600
)
@Composable
fun CreateDictationGame() {
    val viewModel = hiltViewModel<CreateDictationGameViewModel>()
    var txtAddress by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Dictation game",
            fontSize = 30.sp,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Enter .txt file address",
            fontSize = 20.sp,
            style = TextStyle(
                fontFamily = FontFamily.Monospace
            )
        )
        TextField(
            value = txtAddress,
            onValueChange = {
                txtAddress = it
            }
        )
        val clipboardManager = LocalClipboardManager.current
        Button(
            onClick = { txtAddress = clipboardManager.getText().toString() }
        ) {
            Text(text = "Paste from Clipboard")
        }
        Button(
            onClick = { viewModel.onStartButton() }
        ) {
            Text(text = "Start")
        }
    }
}

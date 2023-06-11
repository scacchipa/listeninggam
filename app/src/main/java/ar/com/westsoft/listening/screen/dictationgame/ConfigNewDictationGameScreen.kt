package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
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
import ar.com.westsoft.listening.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    widthDp = 400,
    heightDp = 600
)
@Composable
fun ConfigNewDictationGameScreen(
    playGame: (Long) -> Unit = { },
    goBack: () -> Unit = { }
) {
    val viewModel = hiltViewModel<ConfigNewDictationGameViewModel>()

    val gameCreationGameStatus = viewModel.gameCreationGameStatus.collectAsState().value

    when (gameCreationGameStatus) {
        is GameCreationGameStatus.Uninitialized -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var txtAddress by remember { mutableStateOf("") }
                var title by remember { mutableStateOf("") }
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
                    text = "Title:",
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    )
                )
                TextField(
                    value = title,
                    onValueChange = { title = it }
                )
                Text(
                    text = "Enter .txt file address:",
                    fontSize = 20.sp,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    )
                )
                TextField(
                    value = txtAddress,
                    onValueChange = { txtAddress = it }
                )
                val clipboardManager = LocalClipboardManager.current
                Button(
                    onClick = { txtAddress = clipboardManager.getText().toString() }
                ) {
                    Text(text = "Paste from Clipboard")
                }
                Button(
                    onClick = {
                        if (txtAddress.isNotBlank()) {
                            viewModel.onStartButton(title, txtAddress)
                        } else {
                            viewModel.onStartButton(
                                title = BuildConfig.DEBUG_DICTATION_TEXT_TITLE
                                    ?: title,
                                address = BuildConfig.DEBUG_DICTATION_TEXT_ADDRESS
                                    ?:txtAddress

                                // title = "The adventure of Tom Sawyer",
                                // address = "http://medicamentosrothlin.com.ar/app/mac/"
                                // address = "https://www.gutenberg.org/files/74/74-0.txt"
                                //address = "http://d-scholarship.pitt.edu/5725/6/licence.txt"
                            )
                        }
                    }
                ) {
                    Text(text = "Start")
                }
            }
        }
        is GameCreationGameStatus.Completed -> {
            AdviceScreen("Stating new game ...")
            LaunchedEffect(Unit) {
                playGame(gameCreationGameStatus.gui)
            }
        }
        is GameCreationGameStatus.Error -> {
            AdviceScreen("Navigation ...")
            LaunchedEffect(Unit) { goBack() }
        }
        is GameCreationGameStatus.IsDownloading -> {
            AdviceScreen("Downloading ...")
        }
    }
}

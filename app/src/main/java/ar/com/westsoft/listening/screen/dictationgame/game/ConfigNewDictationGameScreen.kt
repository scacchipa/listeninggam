package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.BuildConfig
import ar.com.westsoft.listening.screen.dictationgame.AdviceScreen
import ar.com.westsoft.listening.screen.dictationgame.navigation.ConfigNewDictationGameViewModel
import ar.com.westsoft.listening.screen.dictationgame.navigation.GameCreationGameStatus
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigNewDictationGameScreen(
    playGame: (gui: Long) -> Unit,
    goBack: () -> Unit
) {
    val viewModel = hiltViewModel<ConfigNewDictationGameViewModel>()

    val value = viewModel.gameCreationGameStatus.collectAsState().value

    when (value) {
        is GameCreationGameStatus.Uninitialized -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var txtAddress by remember { mutableStateOf("") }
                var title by remember { mutableStateOf("") }
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = "Dictation game",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "Title:",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextField(
                    value = title,
                    onValueChange = { title = it }
                )
                Text(
                    text = "Enter .txt file address:",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextField(
                    value = txtAddress,
                    onValueChange = { txtAddress = it }
                )
                val clipboardManager = LocalClipboardManager.current
                Button(
                    onClick = { txtAddress = clipboardManager.getText().toString() }
                ) {
                    Text(
                        text = "Paste from Clipboard",
                        style = MaterialTheme.typography.labelMedium
                    )
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
                                    ?: txtAddress

                                // title = "The adventure of Tom Sawyer",
                                // address = "http://medicamentosrothlin.com.ar/app/mac/"
                                // address = "https://www.gutenberg.org/files/74/74-0.txt"
                                //address = "http://d-scholarship.pitt.edu/5725/6/licence.txt"
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        is GameCreationGameStatus.Completed -> {
            AdviceScreen("Stating new game ...")
            LaunchedEffect(Unit) { playGame(value.gui) }
        }

        is GameCreationGameStatus.Error -> {
            AdviceScreen("Error ...")
            LaunchedEffect(Unit) {
                delay(2000)
                goBack()
            }
        }

        is GameCreationGameStatus.IsDownloading -> {
            AdviceScreen("Downloading ...")
        }
    }
}

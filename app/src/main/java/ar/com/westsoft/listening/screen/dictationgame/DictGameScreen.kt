package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DictGameScreen() {
    val viewModel = hiltViewModel<DictationViewModel>()
    Surface {
        val requester = remember { FocusRequester() }
        Column(
            modifier = Modifier
                .onKeyEvent { keyEvent ->
                    viewModel.onKeyEvent(keyEvent)
                    true
                }
                .focusRequester(requester)
                .focusable(),
        ) {
            Button(
                onClick = { viewModel.onButtonClicked() },
            ) {
                Text("Speak Out")
            }
            val viewState = viewModel.dictationGameStateFlow.collectAsState()

            println("*** Text to read: " + viewState.value)

            Text(
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                ),
                text = "Paragraph: ${viewState.value.paragraphIdx}"
            )
            Text(
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                ),
                text = "Column: ${viewState.value.cursorColumn}"
            )

            ClickableText(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(color = Color.Yellow),
                text = viewState.value.textToShow,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                ),
                onClick = { offset ->
                    viewModel.onLetterClicked(offset)
                },
            )
        }
        LaunchedEffect(Unit) {
            requester.requestFocus()
        }
    }
}

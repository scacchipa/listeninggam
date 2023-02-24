package ar.com.westsoft.listening

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.screen.DictationViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DictationScreen() {
    val viewModel = hiltViewModel<DictationViewModel>()
    Surface() {
        Column(
        ) {
            Button(
                onClick = { viewModel.onInitializeProgress() }
            ) {
                Text(text = "Initialize progress")
            }
            Button(
                onClick = { viewModel.onButtonClicked() },
            ) {
                Text("Speak Out")
            }
            val textToShowState = viewModel.annotatedStringStateFlow.collectAsState()
            val requester = remember { FocusRequester() }
            Text(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .background(color = Color.Yellow)
                    .onKeyEvent { keyEvent ->
                        println("**** " + keyEvent)
                        viewModel.onKeyEvent(keyEvent)
                        true
                    }
                    .focusRequester(requester)
                    .focusable(),
                text = textToShowState.value,
                fontSize = 20.sp,
                lineHeight = 30.sp,
            )
            LaunchedEffect(Unit) {
                requester.requestFocus()
            }
        }
    }
}


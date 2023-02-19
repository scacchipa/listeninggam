package ar.com.westsoft.listening

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    Surface {
        Column {
            Button(
                onClick = { viewModel.onButtonClicked() },
            ) {
                Text("Speak Out")
            }
            val textToShowState = viewModel.annotatedStringStateFlow.collectAsState()
            Text(
                text = textToShowState.value,
                fontSize = 50.sp,
                lineHeight = 60.sp,
            )
        }
    }
}

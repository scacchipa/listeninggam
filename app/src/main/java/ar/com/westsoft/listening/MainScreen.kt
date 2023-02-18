package ar.com.westsoft.listening

import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    Surface {
        Text("Hello World!")
        Button(
            onClick = { viewModel.onButtonClicked() }
        ) {
            Text("Speak Out")
        }
    }
}

package ar.com.westsoft.listening

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.com.westsoft.listening.screen.menu.NavigationScreen
import dagger.hilt.android.AndroidEntryPoint

// TODO: Delete speak out button
// TODO: Add a config button in the DictGameScreen and to open a ConfigScreen
// TODO: Add option to modified the count the game read
// TODO: Add option to start to read before the cursor position.
// TODO: Add option to download a txt file to the SelectDictationGameScreen
// TODO: Add the option to paste a text from clipboard in the SelectDictationGameScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListeningTheme {
                NavigationScreen()
            }
        }
    }
}

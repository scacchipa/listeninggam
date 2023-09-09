package ar.com.westsoft.listening

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.com.westsoft.listening.screen.menu.NavigationScreen
import dagger.hilt.android.AndroidEntryPoint

// TODO: Add option to modified the count the game read
// TODO: Add option to start to read before the cursor position.
// TODO: Add option to download a txt file to the SelectDictationGameScreen
// TODO: Add the option to paste a text from clipboard in the SelectDictationGameScreen
// TODO: Catch the back button and go out of the dictation game

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

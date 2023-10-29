package ar.com.westsoft.listening

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.com.westsoft.listening.screen.menu.NavigationScreen
import dagger.hilt.android.AndroidEntryPoint

// TODO: Add option to download a txt file to the SelectDictationGameScreen
// TODO: Add the option to paste a text from clipboard in the SelectDictationGameScreen
// TODO: Toggle the key of the keyboard and reset the the cursor advance.
// TODO: Add functionality to Word Button.
// TODO: Modify readWordBeforeCursor condition to be able to start to read from the cursor
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

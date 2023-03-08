package ar.com.westsoft.listening.screen.menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.com.westsoft.listening.screen.RoundedFrame
import ar.com.westsoft.listening.screen.dictationgame.NavDictationGameScreen
import ar.com.westsoft.listening.screen.dictationgame.SelectGameScreen

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    RoundedFrame(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.SelectGame.name
        ) {
            composable(route = Routes.SelectGame.name) {
                SelectGameScreen(
                    navDictationGame = { navController.navigate(Routes.DictationGame.name) }
                )
            }
            composable(route = Routes.DictationGame.name) {
                NavDictationGameScreen()
            }
        }
    }
}

enum class Routes {
    SelectGame,
    DictationGame,
}

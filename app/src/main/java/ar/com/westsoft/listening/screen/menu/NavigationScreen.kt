package ar.com.westsoft.listening.screen.menu

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.com.westsoft.listening.screen.dictationgame.NavDictationGameScreen

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

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

enum class Routes {
    SelectGame,
    DictationGame,
}

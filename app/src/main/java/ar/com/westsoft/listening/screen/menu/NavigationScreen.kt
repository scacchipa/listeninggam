package ar.com.westsoft.listening.screen.menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.com.westsoft.listening.screen.RoundedFrame
import ar.com.westsoft.listening.screen.dictationgame.CreateDictationGame
import ar.com.westsoft.listening.screen.dictationgame.StartANewGameScreen

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    RoundedFrame(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.MainMenu.name
        ) {
            composable(route = Routes.MainMenu.name) {
                MainMenuScreen(
                    navStartANewGame = { navController.navigate(Routes.StartANewGame.name) },
                    navLoadAGame = { navController.navigate(Routes.LoadANewGame.name) }
                )
            }
            composable(route = Routes.StartANewGame.name) {
                StartANewGameScreen(
                    navCreateDictationGame = { navController.navigate(Routes.CreateDictationGame.name) }
                )
            }
            composable(route = Routes.LoadANewGame.name) {
                LoadANewGameScreen()
            }
            composable(route = Routes.CreateDictationGame.name) {
                CreateDictationGame()
            }

        }
    }
}

enum class Routes {
    MainMenu,
    StartANewGame,
    CreateDictationGame,
    LoadANewGame
}

@Composable
fun LoadANewGameScreen() {

}



package ar.com.westsoft.listening.screen.menu

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.com.westsoft.listening.screen.dictationgame.game.ConfigNewDictationGameScreen
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameMainScreen
import ar.com.westsoft.listening.screen.dictationgame.navigation.NavDictationGameViewModel
import ar.com.westsoft.listening.screen.dictationgame.navigation.SelectDictationGameScreen

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<NavDictationGameViewModel>()

    NavHost(
        navController = navController,
        startDestination = Routes.SelectGame.name,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        composable(route = Routes.SelectGame.name) {
            SelectDictationGameScreen(
                playGame = { gui ->
                    navController.navigate(Routes.DictationGame.name + "?gui=$gui")
                },
                goBack = { navController.navigateUp() },
                openConfigNewGame = {
                    navController.navigate(Routes.ConfigNewGame.name)
                }
            )
        }
        composable(route = Routes.ConfigNewGame.name) {
            ConfigNewDictationGameScreen(
                playGame = { gui ->
                    navController.navigate(Routes.DictationGame.name + "?gui=$gui") {
                        popUpTo(Routes.SelectGame.name)
                    }
                },
                goBack = { navController.navigateUp() }
            )
        }
        composable(
            route = Routes.DictationGame.name + "?gui={gui}",
            arguments = listOf(navArgument("gui") { type = NavType.LongType })
        ) {
            val gui = it.arguments?.getLong("gui") ?: return@composable Text("GUI ERROR ...")

            var isConfig by remember { mutableStateOf(false) }
            if (isConfig) {
                DictGameMainScreen(
                    goBack = { navController.popBackStack(Routes.DictationGame.name, false) }
                )
            } else {
                viewModel.onSetupGame(gui) { isConfig = true }
            }
        }
    }
}

enum class Routes {
    SelectGame,
    ConfigNewGame,
    DictationGame,
}

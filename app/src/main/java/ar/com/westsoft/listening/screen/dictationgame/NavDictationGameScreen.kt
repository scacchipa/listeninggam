package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavDictationGameScreen() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DictRoutes.DictMainMenu.name
    ) {
        composable(route = DictRoutes.DictMainMenu.name) {
            MainMenuDictGameScreen(
                navStartANewGame = { navController.navigate(DictRoutes.StartNewGame.name) },
                navLoadAGame = { navController.navigate(DictRoutes.LoadGame.name) }
            )
        }
        composable(route = DictRoutes.StartNewGame.name) {
            ConfigNewDictationGameScreen(
                playGame = { gui ->
                    navController.navigate("${DictRoutes.DictationGame.name}/$gui")
                },
                goBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = DictRoutes.LoadGame.name) {
            SelectDictationGameScreen(
                playGame = { gui ->
                    navController.navigate("${DictRoutes.DictationGame.name}/$gui")
                },
                goBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = "${DictRoutes.DictationGame.name}/{gui}",
            arguments = listOf(navArgument("gui") {
                defaultValue = -1
                type = NavType.LongType
            })
        ) { navBackStackEntry ->
            val gui: Long? = navBackStackEntry.arguments?.getLong("gui")
            gui?.let { it ->
                val viewModel = hiltViewModel<DictationViewModel>()
                viewModel.onInitializeProgress(it)
                DictGameScreen()
            } ?: let {
                navController.navigateUp()
            }
        }
    }
}

enum class DictRoutes {
    DictMainMenu,
    StartNewGame,
    LoadGame,
    DictationGame
}

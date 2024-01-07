package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.com.westsoft.listening.screen.dictationgame.game.ConfigNewDictationGameScreen
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameMainScreen

@Composable
fun NavDictationGameScreen() {
    val viewModel = hiltViewModel<NavDictationGameViewModel>()
    MaterialTheme(
        typography = Typography(
            bodySmall = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            ),
            bodyMedium = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp
            ),
            bodyLarge = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp
            ),
            labelSmall = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            labelMedium = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            labelLarge = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    ) {
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
                        navController.navigate(DictRoutes.DictationGame.name + "?gui=$gui")
                               },
                    goBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(route = DictRoutes.LoadGame.name) {
                SelectDictationGameScreen(
                    playGame = { gui ->
                        navController.navigate(DictRoutes.DictationGame.name + "?gui=$gui")
                    },
                    goBack = { navController.navigateUp() }
                )
            }
            composable(
                route = DictRoutes.DictationGame.name + "?gui={gui}",
                arguments = listOf(navArgument("gui") { type = NavType.LongType })
            ) {
                val gui = it.arguments?.getLong("gui") ?: return@composable Text("GUI ERROR ...")

                var isConfig by remember { mutableStateOf(false) }
                if (isConfig) {
                    DictGameMainScreen(
                        goBack = { navController.popBackStack(DictRoutes.DictMainMenu.name, false) }
                    )
                } else {
                    viewModel.onSetupGame(gui) { isConfig = true }
                }
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

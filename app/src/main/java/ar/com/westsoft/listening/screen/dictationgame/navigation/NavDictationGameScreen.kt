package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.com.westsoft.listening.screen.dictationgame.game.ConfigNewDictationGameScreen
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameMainScreen

@Composable
fun NavDictationGameScreen() {
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
                    playGame = { navController.navigate(DictRoutes.DictationGame.name) },
                    goBack = { navController.navigateUp() }
                )
            }
            composable(route = DictRoutes.LoadGame.name) {
                SelectDictationGameScreen(
                    playGame = { navController.navigate(DictRoutes.DictationGame.name) },
                    goBack = { navController.navigateUp() }
                )
            }
            composable(route = DictRoutes.DictationGame.name) {
                DictGameMainScreen(
                    goBack = { navController.popBackStack(DictRoutes.DictMainMenu.name, false) }
                )
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

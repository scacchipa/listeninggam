package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
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
                gui?.let {
                    val viewModel = hiltViewModel<DictGameMainViewModel>()
                    viewModel.onInitializeProgress(it)
                    DictGameMainScreen()
                } ?: let {
                    navController.navigateUp()
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

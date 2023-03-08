package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectDictationGameScreen(
    playGame: (gui: Long) -> Unit = { },
    goBack: () -> Unit = { }
) {
    val viewModel = hiltViewModel<SelectDictationGameViewModel>()

    val games = viewModel.games.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        content = {
            items(games.size) { idx ->
                Button(
                    onClick = { playGame(games[idx].gui) }
                ) {
                    Text(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        text = "${games[idx].gui} ${games[idx].title} " +
                                String.format("%.1f", games[idx].progressPercent) + "%"
                    )
                }
            }
            item {
                Button(onClick = { goBack() }) {
                    Text(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Monospace,
                        text = "Back"
                    )
                }
            }
        }
    )
}

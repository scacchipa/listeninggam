package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectDictationGameScreen(
    playGame: (gui: Long) -> Unit,
    goBack: () -> Unit
) {
    val viewModel = hiltViewModel<SelectDictationGameViewModel>()

    val games = viewModel.games.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        content = {
            items(games.size) { idx ->
                SelectorGameButton(
                    game = games[idx],
                    onPlay = { playGame(games[idx].gui) },
                    onDelete = { viewModel.onDeleteGame(games[idx]) }
                )
            }
            item {
                Button(onClick = { goBack() }) {
                    Text(
                        text = "Back",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    )
}

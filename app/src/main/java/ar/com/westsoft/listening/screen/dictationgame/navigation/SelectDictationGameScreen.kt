package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SelectDictationGameScreen(
    playGame: (gui: Long) -> Unit,
    onDownloadBookClicked: () -> Unit
) {
    val viewModel = hiltViewModel<SelectDictationGameViewModel>()

    val games = viewModel.games.collectAsState().value

    Column(
        modifier = Modifier.fillMaxSize(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectDictationGameTopMenu(
            onDownloadClicked = onDownloadBookClicked
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1f)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                items(games.size) { idx ->
                    SelectorGameButton(
                        game = games[idx],
                        onPlay = { playGame(games[idx].gui) },
                        onDelete = { viewModel.onDeleteGame(games[idx]) }
                    )
                }
            }
        )
    }
}

@Composable
fun SelectDictationGameTopMenu(
    onDownloadClicked: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Button(
            onClick = { menuExpanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            )
        ) {
            Text(
                text = "Book",
                color = Color.White,
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            modifier = Modifier.background(Color.Black)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Download",
                        color = Color.White,
                        fontSize = 28.sp,
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                onClick = {
                    menuExpanded = false
                    onDownloadClicked()
                }
            )
        }
    }
}

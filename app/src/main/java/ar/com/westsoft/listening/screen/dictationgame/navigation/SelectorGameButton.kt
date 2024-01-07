package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.com.westsoft.listening.data.game.DictationGameHeader

@Composable
fun SelectorGameButton(
    game: DictationGameHeader,
    onPlay: () -> Unit,
    onDelete: () -> Unit
) {
    Row {
        Button(
            onClick = { onPlay() }
        ) {
            Text(
                text = "${game.gui} ${game.title} " +
                        String.format("%.1f", game.progressRate * 100) + "%",
                style = MaterialTheme.typography.bodyMedium
            )
            Icon(
                painter = painterResource(id = android.R.drawable.ic_media_play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onPlay() }
            )
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_delete),
                contentDescription = "Delete",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onDelete() }
            )
        }
    }
}

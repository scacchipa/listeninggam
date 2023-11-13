package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.data.game.SimpleCursorPos
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun GameConsoleScreen(parentWidthPx: Float) {
    val viewModel = hiltViewModel<GameConsoleViewModel>()
    val viewState by viewModel.cursorPosStateFlow.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val horizontalScrollState = rememberScrollState()

    val localDensity = LocalDensity.current
    var heightPx by remember { mutableStateOf(0f) }
    var heightDp by remember { mutableStateOf(0.dp) }
    var widthPx by remember { mutableStateOf(0f) }
    var widthDp by remember { mutableStateOf(0.dp) }

    SideEffect {
        coroutineScope.launch {
            val startComplexCursorPos = viewModel.getStartParagraphToShow(
                simpleCursor = SimpleCursorPos(viewState.paragraphIdx, viewState.letterPos),
                numberRowAbove = 5
            ) ?: return@launch

            listState.animateScrollToItem(
                index = max(startComplexCursorPos.paragraphIdx ?: 0, 0),
                scrollOffset = with(localDensity) {
                    (startComplexCursorPos.row ?: 0) * 20.sp.roundToPx()
                }.toInt()
            )

            horizontalScrollState.scrollTo(viewModel.getHorizontalShift(
                startComplexCursorPos.column ?: 0, parentWidthPx.toInt(), widthPx.toInt()
            ))
        }
    }

    Text(
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        ),
        text = "Paragraph: ${viewState.paragraphIdx}"
    )

    Text(
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        ),
        text = "Column: ${viewState.letterPos}"
    )

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(horizontalScrollState)
            .onGloballyPositioned { coordinates ->
                heightPx = coordinates.size.height.toFloat()
                heightDp = with(localDensity) {
                    coordinates.size.height.toDp()
                }
                widthPx = coordinates.size.width.toFloat()
                widthDp = with(localDensity) {
                    coordinates.size.width.toDp()
                }
            }
    ) {
        items(viewModel.getProgressListSize() ?: 0) { idx ->
            ClickableText(
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    lineHeight = 20.sp
                ),
                text = viewModel.getFormatText(idx),
                onClick = {
                    viewModel.onParagraphClick(idx)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

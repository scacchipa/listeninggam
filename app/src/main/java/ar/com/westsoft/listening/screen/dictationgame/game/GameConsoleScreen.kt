package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun GameConsoleScreen(parentWidthPx: Float) {
    val viewModel = hiltViewModel<GameConsoleViewModel>()
    val viewState by viewModel.getDictationGameStateFlow().collectAsState(
        initial = DictGameState(
            0,
            null,
            null,
            AnnotatedString(""),
            DictationGameRecord()
        )
    )
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
            val firstVisibleItem = listState.firstVisibleItemIndex
            val paragraphToShow = viewState.paragraphIdx
            val visibleItemCount = listState.layoutInfo.visibleItemsInfo.size - 1
            val lastVisibleItem = firstVisibleItem + visibleItemCount

            if (paragraphToShow !in firstVisibleItem..lastVisibleItem - visibleItemCount / 2) {
                listState.animateScrollToItem(
                    max(
                        viewState.paragraphIdx - visibleItemCount / 2,
                        0
                    )
                )
            }

            println("parent: $parentWidthPx")
            println("width: $widthPx")
            println("cursor col: ${viewState.cursorCol}")

            val cursorCol = viewState.cursorCol
            val columnPerPage = viewModel.getSetting().columnPerPage.value
            val leftMargin = 10
            val rightMargin = 15

            if (parentWidthPx < widthPx && cursorCol != null) {
                when {
                    cursorCol < leftMargin ->
                        horizontalScrollState.scrollTo(0)

                    cursorCol > columnPerPage - rightMargin ->
                        horizontalScrollState.scrollTo(-(parentWidthPx - widthPx).toInt())

                    else -> horizontalScrollState.scrollTo(
                        -(parentWidthPx - widthPx).toInt()
                                / (columnPerPage - leftMargin - rightMargin)
                                * (cursorCol - leftMargin)
                    )
                }
            }
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
        text = "Column: ${viewState.pos}"
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
        val progressList = viewState.dictationGameRecord.dictationProgressList

        println(viewState.cursorCol)

        items(progressList.size) { idx ->
            if (viewState.paragraphIdx != idx) {
                ClickableText(
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp
                    ),
                    text = viewModel.getFormatText(progressList[idx].progressTxt),
                    onClick = {
                        viewModel.onParagraphClick(idx)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                ClickableText(
                    modifier = Modifier
                        .background(color = Color.Yellow),
                    text = viewState.textToShow,
                    softWrap = false,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp
                    ),
                    onClick = { offset ->
                        viewModel.onLetterClicked(offset)
                    }
                )
            }
        }
    }
}

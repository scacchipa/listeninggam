package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun GameConsoleScreen() {
    val viewModel = hiltViewModel<GameConsoleViewModel>()
    val viewState = viewModel.dictationGameStateFlow.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        coroutineScope.launch {
            val firstVisibleItem = listState.firstVisibleItemIndex
            val paragraphToShow = viewState.value.paragraphIdx
            val visibleItemCount = listState.layoutInfo.visibleItemsInfo.size - 1
            val lastVisibleItem = firstVisibleItem + visibleItemCount

            if (paragraphToShow !in firstVisibleItem..lastVisibleItem - visibleItemCount / 2) {
                listState.animateScrollToItem(
                    max(
                        viewState.value.paragraphIdx - visibleItemCount / 2,
                        0
                    )
                )
            }
        }
    }

    Text(
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        ),
        text = "Paragraph: ${viewState.value.paragraphIdx}"
    )

    Text(
        style = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp
        ),
        text = "Column: ${viewState.value.cursorColumn}"
    )

    LazyColumn(state = listState) {
        val progressList = viewState.value.dictationGameRecord.dictationProgressList

        items(progressList.size) { idx ->
            if (viewState.value.paragraphIdx != idx) {
                ClickableText(
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp
                    ),
                    text = AnnotatedString(progressList[idx].progressTxt.concatToString()),
                    onClick = {
                        viewModel.onParagraphClick(idx)
                    }
                )
            } else {
                ClickableText(
                    modifier = Modifier
                        .background(color = Color.Yellow),
                    text = viewState.value.textToShow,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp
                    ),
                    onClick = { offset ->
                        viewModel.onLetterClicked(offset)
                    },
                )
            }
        }
    }
}

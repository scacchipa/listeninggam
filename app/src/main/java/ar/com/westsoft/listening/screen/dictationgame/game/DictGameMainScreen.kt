package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettingScreen
import ar.com.westsoft.listening.screen.keyboard.MainKeyBoard
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun DictGameMainScreen() {
    val viewModel = hiltViewModel<DictGameMainViewModel>()

    val isShowingOptions = viewModel.isShowingPreference.collectAsState()

    if (isShowingOptions.value) {
        DictGameSettingScreen(
            onBack = {
                viewModel.onPreferenceClosed()
            }
        )
    } else {
        val localDensity = LocalDensity.current

        // Create element height in pixel state
        var heightPx by remember { mutableStateOf(0f) }

        // Create element height in dp state
        var heightDp by remember { mutableStateOf(0.dp) }

        // Create element height in pixel state
        var widthPx by remember { mutableStateOf(0f) }

        // Create element height in dp state
        var widthDp by remember { mutableStateOf(0.dp) }

        val sideKey = widthDp / (10.5f + 2f)

        Box(
            Modifier
                .fillMaxSize()
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
            val requester = remember { FocusRequester() }
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .onKeyEvent { keyEvent ->
                        viewModel.onKeyEvent(keyEvent)
                        true
                    }
                    .focusRequester(requester)
                    .focusable()
                    .size(
                        width = widthDp,
                        height = heightDp - sideKey * 5
                    )
            ) {
                val viewState = viewModel.dictationGameStateFlow.collectAsState()

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

                IconButton(
                    onClick = {
                        viewModel.onSettingButtonClicked()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Preference"
                    )
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

            MainKeyBoard(
                modifier = Modifier
                    .offset(
                        x = 0.dp,
                        y = heightDp - sideKey * 5
                    ),
                sideKey = sideKey
            )

            LaunchedEffect(Unit) {
                requester.requestFocus()
            }
        }
    }
}

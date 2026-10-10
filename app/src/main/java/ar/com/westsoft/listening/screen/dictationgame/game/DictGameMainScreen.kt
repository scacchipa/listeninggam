package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.scacchipa.keyboard.screen.KeyboardLayout
import ar.com.scacchipa.keyboard.screen.KeyboardType
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.dictionary.screen.DictionaryScreen
import ar.com.westsoft.listening.dictionary.source.WiktionaryItem
import ar.com.westsoft.listening.screen.dictationgame.navigation.DictGameTopBar
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettingScreen

private data class SpeedOption(
    val label: String,
    val color: Color
)

private val speedOptions = mapOf(
    SpeedLevelPreference.LOW_SPEED_LEVEL to SpeedOption("0.5x", Color.Green),
    SpeedLevelPreference.MEDIUM_SPEED_LEVEL to SpeedOption("0.75x", Color.Yellow),
    SpeedLevelPreference.NORMAL_SPEED_LEVEL to SpeedOption("1.0x", Color.Magenta),
    SpeedLevelPreference.HIGH_SPEED_LEVEL to SpeedOption("1.25x", Color.Red),
    SpeedLevelPreference.VERY_HIGH_SPEED_LEVEL to SpeedOption("1.5x", Color.Cyan),
    SpeedLevelPreference.MAX_SPEED_LEVEL to SpeedOption("2.0x", Color.Blue),
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DictGameMainScreen(
    goBack: () -> Unit
) {
    val viewModel = hiltViewModel<DictGameMainViewModel>()
    val isShowingOptions = viewModel.isShowingPreference.collectAsState()
    val isShowingDictionary = viewModel.isShowingDictionary.collectAsState()
    val dictionaryWord = viewModel.dictionaryWord.collectAsState()
    val dictionaryDefinition = viewModel.dictionaryDefinition.collectAsState()
    val dictionaryError = viewModel.dictionaryError.collectAsState()

    BackHandler(true) {
        when {
            isShowingOptions.value -> viewModel.onPreferenceClosed()
            isShowingDictionary.value -> viewModel.onDictionaryClosed()
            else -> goBack()
        }
    }

    when {
        isShowingOptions.value -> DictGameOptionsContent(
            onPreferenceClosed = { viewModel.onPreferenceClosed() }
        )
        isShowingDictionary.value -> DictGameDictionaryContent(
            dictionaryWord = dictionaryWord.value,
            dictionaryDefinition = dictionaryDefinition.value,
            dictionaryError = dictionaryError.value,
            onDictionaryClosed = { viewModel.onDictionaryClosed() },
            onWordSelected = { word -> viewModel.onWordSelected(word) }
        )
        else -> DictGamePlayContent(
            goBack = goBack,
            viewModel = viewModel
        )
    }
}

@Composable
private fun DictGameOptionsContent(
    onPreferenceClosed: () -> Unit
) {
    Row {
        DictGameSettingScreen(
            onBack = onPreferenceClosed
        )
    }
}

@Composable
private fun DictGameDictionaryContent(
    dictionaryWord: String,
    dictionaryDefinition: List<WiktionaryItem>,
    dictionaryError: String?,
    onDictionaryClosed: () -> Unit,
    onWordSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DictGameTopBar(
            leadingButton = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDictionaryClosed
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to game",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = dictionaryWord,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        )
        DictionaryScreen(
            word = dictionaryWord,
            items = dictionaryDefinition,
            error = dictionaryError,
            onWordClick = onWordSelected,
            onBack = onDictionaryClosed
        )
    }
}

@Composable
private fun DictGamePlayContent(
    goBack: () -> Unit,
    viewModel: DictGameMainViewModel
) {
    val localDensity = LocalDensity.current

    // Create element height in pixel state
    var heightPx by remember { mutableFloatStateOf(0f) }

    // Create element height in dp state
    var heightDp by remember { mutableStateOf(0.dp) }

    // Create element height in pixel state
    var widthPx by remember { mutableFloatStateOf(0f) }

    // Create element height in dp state
    var widthDp by remember { mutableStateOf(0.dp) }

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
        var keyboardHeightDp by remember { mutableStateOf(0.dp) }

        Column(
            modifier = Modifier
                .size(
                    width = widthDp,
                    height = heightDp - keyboardHeightDp
                )
        ) {
            DictGameTopBar(
                leadingButton = {
                    IconButton(
                        onClick = goBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to main menu",
                            tint = Color.White
                        )
                    }
                },
                trailingButton = {
                    IconButton(
                        onClick = { viewModel.onSettingButtonClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Preference",
                            tint = Color.White
                        )
                    }
                },
                content = {
                    val speedLevel = viewModel.speedLevelState.collectAsState().value
                    val speedMenuExpanded by viewModel.isShowingSpeedMenu.collectAsState()

                    val currentOption = speedOptions[speedLevel.value]
                    val currentSpeedText = currentOption?.label ?: "1.0x"
                    val currentSpeedColor = currentOption?.color ?: Color.White

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box {
                            Button(
                                onClick = { viewModel.onSpeedMenuToggle(true) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.DarkGray
                                )
                            ) {
                                Text(
                                    text = currentSpeedText,
                                    color = currentSpeedColor,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }

                            DropdownMenu(
                                expanded = speedMenuExpanded,
                                onDismissRequest = { viewModel.onSpeedMenuToggle(false) },
                                modifier = Modifier.background(Color.Black)
                            ) {
                                speedOptions.forEach { (level, option) ->
                                    DropdownMenuItem(
                                        text = { Text(option.label, color = option.color, style = MaterialTheme.typography.titleMedium) },
                                        onClick = {
                                            viewModel.onSpeedSpeechSelected(level)
                                        }
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.onDictButtonClicked() },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("DICT")
                        }
                    }
                }
            )

            GameConsoleScreen(parentWidthPx = widthPx)
        }

        KeyboardLayout(
            modifier = Modifier
                .offset(
                    x = 0.dp,
                    y = heightDp - keyboardHeightDp
                )
                .onSizeChanged { newSize ->
                    keyboardHeightDp = (newSize.height / localDensity.density).dp
                },
            widthDp = widthDp,
            keyboardType = KeyboardType.BigKey,
            action = { keyEvent -> viewModel.onKeyEvent(keyEvent) },
            onBack = goBack,
            resetToken = viewModel.resetSignal.collectAsState().value
        )
    }
}

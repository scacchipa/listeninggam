package ar.com.westsoft.listening.screen.dictationgame.game

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.scacchipa.keyboard.screen.KeyboardLayout
import ar.com.scacchipa.keyboard.screen.KeyboardType
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.dictionary.screen.DictionaryScreen
import ar.com.westsoft.listening.screen.dictationgame.navigation.DictGameTopBar
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettingScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DictGameMainScreen(
    goBack: () -> Unit
) {
    val viewModel = hiltViewModel<DictGameMainViewModel>()
    val isShowingOptions = viewModel.isShowingPreference.collectAsState()
    val isShowingDictionary = viewModel.isShowingDictionary.collectAsState()
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
        isShowingOptions.value -> {
            Row {
                DictGameSettingScreen(
                    onBack = { viewModel.onPreferenceClosed() }
                )
            }
        }
        isShowingDictionary.value -> {
            DictionaryScreen(
                items = dictionaryDefinition.value,
                error = dictionaryError.value,
                onBack = { viewModel.onDictionaryClosed() }
            )
        }
        else -> {
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
                val requester = remember { FocusRequester() }
                var keyboardHeightDp by remember { mutableStateOf(0.dp) }

                Column(
                    modifier = Modifier
                        .onKeyEvent { keyEvent ->
                            if (keyEvent.key == Key.Back) {
                                goBack()
                            }
                            viewModel.onKeyEvent(keyEvent)
                            true
                        }
                        .focusRequester(requester)
                        .focusable()
                        .size(
                            width = widthDp,
                            height = heightDp - keyboardHeightDp
                        )
                ) {
                    DictGameTopBar(
                        leadingButton = {
                            IconButton(
                                onClick = { goBack() }
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
                            var speedMenuExpanded by remember { mutableStateOf(false) }

                            val currentSpeedText = when (speedLevel.value) {
                                SpeedLevelPreference.LOW_SPEED_LEVEL -> "0.5x"
                                SpeedLevelPreference.MEDIUM_SPEED_LEVEL -> "0.75x"
                                SpeedLevelPreference.NORMAL_SPEED_LEVEL -> "1.0x"
                                SpeedLevelPreference.HIGH_SPEED_LEVEL -> "1.25x"
                                SpeedLevelPreference.VERY_HIGH_SPEED_LEVEL -> "1.5x"
                                SpeedLevelPreference.MAX_SPEED_LEVEL -> "2.0x"
                            }

                            val currentSpeedColor = when (speedLevel.value) {
                                SpeedLevelPreference.LOW_SPEED_LEVEL -> Color.Green
                                SpeedLevelPreference.MEDIUM_SPEED_LEVEL -> Color.Yellow
                                SpeedLevelPreference.NORMAL_SPEED_LEVEL -> Color.Magenta
                                SpeedLevelPreference.HIGH_SPEED_LEVEL -> Color.Red
                                SpeedLevelPreference.VERY_HIGH_SPEED_LEVEL -> Color.Cyan
                                SpeedLevelPreference.MAX_SPEED_LEVEL -> Color.Blue
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box {
                                    Button(
                                        onClick = { speedMenuExpanded = true },
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
                                        onDismissRequest = { speedMenuExpanded = false },
                                        modifier = Modifier.background(Color.Black)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("0.5x", color = Color.Green, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.LOW_SPEED_LEVEL)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("0.75x", color = Color.Yellow, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.MEDIUM_SPEED_LEVEL)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("1.0x", color = Color.Magenta, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.NORMAL_SPEED_LEVEL)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("1.25x", color = Color.Red, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.HIGH_SPEED_LEVEL)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("1.5x", color = Color.Cyan, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.VERY_HIGH_SPEED_LEVEL)
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("2.0x", color = Color.Blue, style = MaterialTheme.typography.titleMedium) },
                                            onClick = {
                                                speedMenuExpanded = false
                                                viewModel.setSpeedLevel(SpeedLevelPreference.MAX_SPEED_LEVEL)
                                            }
                                        )
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
                    resetToken = viewModel.resetSignal.collectAsState().value
                )

                LaunchedEffect(Unit) {
                    requester.requestFocus()
                }
            }
        }
    }
}

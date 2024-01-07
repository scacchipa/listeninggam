package ar.com.westsoft.listening.screen.dictationgame.game

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettingScreen
import ar.com.westsoft.listening.screen.dictationgame.settings.SelectableButton
import ar.com.westsoft.listening.screen.keyboard.MainKeyBoard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DictGameMainScreen(
    goBack: () -> Unit
) {
    val viewModel = hiltViewModel<DictGameMainViewModel>()
    val isShowingOptions = viewModel.isShowingPreference.collectAsState()

    BackHandler(true) {
        Log.d("TAG", "OnBackPressed")
    }

    if (isShowingOptions.value) {
        Row {
            DictGameSettingScreen(
                onBack = { viewModel.onPreferenceClosed() }
            )
        }
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
                        height = heightDp - sideKey * 5
                    )
            ) {
                Row {
                    IconButton(
                        onClick = { viewModel.onSettingButtonClicked() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Preference"
                        )
                    }

                    Row {
                        val settingsState = viewModel.screenStateFlow.collectAsState()

                        val speedLevel = settingsState.value.speedLevelField
                        SelectableButton(
                            settingField = speedLevel,
                            onSelected = {
                                viewModel.setSpeedLevel(SpeedLevelPreference.LOW_SPEED_LEVEL)
                            },
                            value = SpeedLevelPreference.LOW_SPEED_LEVEL,
                            text = "50%",
                            textColor = Color.Green
                        )

                        SelectableButton(
                            settingField = speedLevel,
                            onSelected = {
                                viewModel.setSpeedLevel(SpeedLevelPreference.MEDIUM_SPEED_LEVEL)
                            },
                            value = SpeedLevelPreference.MEDIUM_SPEED_LEVEL,
                            text = "75%",
                            textColor = Color.Yellow
                        )
                        SelectableButton(
                            settingField = speedLevel,
                            onSelected = {
                                viewModel.setSpeedLevel(SpeedLevelPreference.NORMAL_SPEED_LEVEL)
                            },
                            value = SpeedLevelPreference.NORMAL_SPEED_LEVEL,
                            text = "100%",
                            textColor = Color.Magenta
                        )
                        SelectableButton(
                            settingField = speedLevel,
                            onSelected = {
                                viewModel.setSpeedLevel(SpeedLevelPreference.HIGH_SPEED_LEVEL)
                            },
                            value = SpeedLevelPreference.HIGH_SPEED_LEVEL,
                            text = "125%",
                            textColor = Color.Red
                        )
                    }
                }

                GameConsoleScreen(parentWidthPx = widthPx)
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

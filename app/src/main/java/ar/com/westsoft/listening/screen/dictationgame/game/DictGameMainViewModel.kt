package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.input.key.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.domain.dictationgame.engine.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.GetDictSettingFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetSpeedLevelUseCase
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameScreenSettingsState
import ar.com.westsoft.listening.screen.dictationgame.settings.toScreenSettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DictGameMainViewModel @Inject constructor(
    private val keyEventUseCase: KeyEventUseCase,
    private val getSettingFlowUseCase: GetDictSettingFlowUseCase,
    private val setSpeedLevelUseCase: SetSpeedLevelUseCase
) : ViewModel() {

    private val isMutableShowingPreference = MutableStateFlow(false)
    val isShowingPreference = isMutableShowingPreference as StateFlow<Boolean>

    val screenStateFlow: StateFlow<DictGameScreenSettingsState> =
        getSettingFlowUseCase().map { it.toScreenSettingsState() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = runBlocking {
                    getSettingFlowUseCase().first().toScreenSettingsState()
                }
            )

    fun onSettingButtonClicked() {
        viewModelScope.launch {
            isMutableShowingPreference.emit(true)
        }
    }

    fun onPreferenceClosed() {
        viewModelScope.launch {
            isMutableShowingPreference.emit(false)
        }
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        viewModelScope.launch {
            keyEventUseCase(keyEvent)
        }
    }

    fun setSpeedLevel(speedLevel: SpeedLevelPreference) {
        viewModelScope.launch {
            setSpeedLevelUseCase(speedLevel)
        }
    }
}

package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.input.key.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.engine.KeyEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameMainViewModel @Inject constructor(
    private val keyEventUseCase: KeyEventUseCase
) : ViewModel() {

    private val isMutableShowingPreference = MutableStateFlow(false)
    val isShowingPreference = isMutableShowingPreference as StateFlow<Boolean>

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
}

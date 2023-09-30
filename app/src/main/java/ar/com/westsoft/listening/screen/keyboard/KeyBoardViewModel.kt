package ar.com.westsoft.listening.screen.keyboard

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.KeyEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyBoardViewModel @Inject constructor(
    val keyEventUseCase: KeyEventUseCase
): ViewModel() {
    fun onPushedButton(key: Key) {
        viewModelScope.launch {
            keyEventUseCase(
                KeyEvent(
                    nativeKeyEvent = android.view.KeyEvent(
                        NativeKeyEvent.ACTION_DOWN,
                        key.nativeKeyCode
                    )))
        }
    }
}
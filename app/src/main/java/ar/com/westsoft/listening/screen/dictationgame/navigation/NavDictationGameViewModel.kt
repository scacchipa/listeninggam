package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.engine.SetupDictationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavDictationGameViewModel @Inject constructor(
    private val setupGameUseCase: SetupDictationUseCase
): ViewModel(){
    fun onSetupGame(gui: Long, callback: () -> Unit) {
        viewModelScope.launch {
            setupGameUseCase(gui)

            callback.invoke()
        }
    }
}

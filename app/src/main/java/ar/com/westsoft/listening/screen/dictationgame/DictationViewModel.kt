package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.GetDictationGameFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.SetupDictationUseCase
import ar.com.westsoft.listening.domain.dictationgame.SpeakOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictationViewModel @Inject constructor(
    private val setupDictationGameUseCase: SetupDictationUseCase,
    private val GetDictationGameFlowUseCase: GetDictationGameFlowUseCase,
    private val keyEventUseCase: KeyEventUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    val dictationGameStateFlow: StateFlow<AnnotatedString> =
        GetDictationGameFlowUseCase(viewModelScope)

    fun onInitializeProgress(gui: Long) {
        viewModelScope.launch(defaultDispatcher) {
            setupDictationGameUseCase(gui)
            println("**** set game: $gui.")
            println("**** set new DictationGameFlow.")
        }
    }

    fun onButtonClicked() {
        viewModelScope.launch {
            speakOutUseCase()
        }
    }

    fun onLetterClicked(offset: Int) {
        speakOutUseCase(offset = offset)
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        keyEventUseCase(keyEvent)
    }
}

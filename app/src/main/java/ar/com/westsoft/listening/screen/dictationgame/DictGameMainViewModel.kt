package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.input.key.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.GetDictationGameStateFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.MoveToParagraphUseCase
import ar.com.westsoft.listening.domain.dictationgame.SetupDictationUseCase
import ar.com.westsoft.listening.domain.dictationgame.SpeakOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameMainViewModel @Inject constructor(
    private val setupDictationGameUseCase: SetupDictationUseCase,
    getDictationGameStateFlowUseCase: GetDictationGameStateFlowUseCase,
    private val keyEventUseCase: KeyEventUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    private val moveToParagraphUseCase: MoveToParagraphUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    val dictationGameStateFlow: StateFlow<DictGameState> =
        getDictationGameStateFlowUseCase(viewModelScope)

    private val isMutableShowingPreference = MutableStateFlow(false)
    val isShowingPreference = isMutableShowingPreference as StateFlow<Boolean>

    fun onInitializeProgress(gui: Long) {
        viewModelScope.launch(defaultDispatcher) {
            setupDictationGameUseCase(gui)
            println("**** set game: $gui.")
            println("**** set new DictationGameFlow.")
        }
    }

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

    fun onLetterClicked(offset: Int) {
        viewModelScope.launch {
            speakOutUseCase(offset = offset)
        }
    }

    fun onParagraphClick(paragraphIdx: Int) {
        viewModelScope.launch {
            moveToParagraphUseCase(paragraphIdx)
        }
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        viewModelScope.launch {
            keyEventUseCase(keyEvent)
        }
    }
}

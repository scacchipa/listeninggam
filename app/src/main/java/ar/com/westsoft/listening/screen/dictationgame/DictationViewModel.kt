package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.AnnotationStringFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.InitializeDictationUseCase
import ar.com.westsoft.listening.domain.dictationgame.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.SpeakOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictationViewModel @Inject constructor(
    private val initializeDictationGameUseCase: InitializeDictationUseCase,
    private val annotatedStringFlowUseCase: AnnotationStringFlowUseCase,
    private val keyEventUseCase: KeyEventUseCase,
    private val speakOutUseCase: SpeakOutUseCase
) : ViewModel() {

    val annotatedStringStateFlow: StateFlow<AnnotatedString> =
        annotatedStringFlowUseCase()
            .stateIn(viewModelScope, SharingStarted.Eagerly, AnnotatedString(""))

    fun onInitializeProgress() {
        viewModelScope.launch {
            initializeDictationGameUseCase()
        }
    }

    fun onButtonClicked() {
        speakOutUseCase()
    }

    fun onLetterClicked(offset: Int) {
        speakOutUseCase(offset = offset)
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        keyEventUseCase(keyEvent)
    }
}

package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.AnnotationStringFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.FirstAnnotatedStringUseCase
import ar.com.westsoft.listening.domain.dictationgame.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.SetupDictationUseCase
import ar.com.westsoft.listening.domain.dictationgame.SpeakOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class DictationViewModel @Inject constructor(
    private val setupDictationGameUseCase: SetupDictationUseCase,
    private val annotatedStringFlowUseCase: AnnotationStringFlowUseCase,
    private val keyEventUseCase: KeyEventUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    var annotatedStringStateFlow: StateFlow<AnnotatedString> =
        annotatedStringFlowUseCase(viewModelScope)

    fun onInitializeProgress(gui: Long) {
        viewModelScope.launch(defaultDispatcher) {
            setupDictationGameUseCase(gui)
            annotatedStringStateFlow = annotatedStringFlowUseCase(viewModelScope)
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

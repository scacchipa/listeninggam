package ar.com.westsoft.listening

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.SpeechProvider
import ar.com.westsoft.listening.service.ReaderService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val readerService: ReaderService,
    val speechProvider: SpeechProvider
) : ViewModel() {

    val annotatedStringStateFlow: StateFlow<AnnotatedString> =
        readerService.getAnnotatedStringFlow()
            .stateIn(viewModelScope, SharingStarted.Eagerly, AnnotatedString(""))

    fun onButtonClicked() {
        readerService.speakOut(speechProvider.getSpeech())
    }
}

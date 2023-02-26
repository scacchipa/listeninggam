package ar.com.westsoft.listening.screen.dictationgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.CreateANewDictationGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDictationGameViewModel @Inject constructor(
    private val createANewDictationGameUseCase: CreateANewDictationGameUseCase
) : ViewModel() {
    fun onStartButton() {
        viewModelScope.launch {
            createANewDictationGameUseCase("https://www.gutenberg.org/files/74/74-0.txt")
        }
    }
}
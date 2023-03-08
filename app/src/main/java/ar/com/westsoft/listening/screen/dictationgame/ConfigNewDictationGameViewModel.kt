package ar.com.westsoft.listening.screen.dictationgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.CreateNewDictationGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigNewDictationGameViewModel @Inject constructor(
    private val createNewDictationGameUseCase: CreateNewDictationGameUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _gameCreationGameStatus = MutableStateFlow<GameCreationGameStatus>(
        GameCreationGameStatus.Uninitialized
    )
    val gameCreationGameStatus = _gameCreationGameStatus.asStateFlow()

    fun onStartButton(
        title: String,
        address: String
    ) {
        viewModelScope.launch(defaultDispatcher) {
            _gameCreationGameStatus.emit(GameCreationGameStatus.IsDownloading)
            _gameCreationGameStatus.emit(createNewDictationGameUseCase(title, address))
        }
    }
}

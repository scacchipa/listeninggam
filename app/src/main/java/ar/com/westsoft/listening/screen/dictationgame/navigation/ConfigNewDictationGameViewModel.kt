package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.annotation.RawRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.repository.CreateNewDictationGameUseCase
import ar.com.westsoft.listening.domain.dictationgame.repository.OpenEpubUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigNewDictationGameViewModel @Inject constructor(
    private val createNewDictationGameUseCase: CreateNewDictationGameUseCase,
    private val openEpubUseCase: OpenEpubUseCase,
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

        val coroutineExceptionHandler = CoroutineExceptionHandler {_, throwable ->
            throwable.printStackTrace()
            viewModelScope.launch {
                _gameCreationGameStatus.emit(GameCreationGameStatus.Error)
            }
        }

        viewModelScope.launch(defaultDispatcher + coroutineExceptionHandler) {
            _gameCreationGameStatus.emit(GameCreationGameStatus.IsDownloading)
            _gameCreationGameStatus.emit(createNewDictationGameUseCase(title, address))
        }
    }

    fun onBookSelected(@RawRes id: Int) {
        openEpubUseCase(id)
    }
}

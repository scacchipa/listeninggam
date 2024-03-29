package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.di.DefaultDispatcher
import ar.com.westsoft.listening.domain.dictationgame.repository.GetDictationGameLabels
import ar.com.westsoft.listening.domain.dictationgame.repository.DeleteGameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDictationGameViewModel @Inject constructor(
    private val getDictationGameLabels: GetDictationGameLabels,
    private val deleteGame: DeleteGameUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _games = MutableStateFlow<List<DictationGameHeader>>(emptyList())
    val games = _games.asStateFlow()

    init{
        viewModelScope.launch {
            _games.emit(getDictationGameLabels())
        }
    }

    fun onDeleteGame(gameHeader: DictationGameHeader) {
        viewModelScope.launch(defaultDispatcher) {
            deleteGame(gameHeader)
            _games.emit(getDictationGameLabels())
        }
    }
}

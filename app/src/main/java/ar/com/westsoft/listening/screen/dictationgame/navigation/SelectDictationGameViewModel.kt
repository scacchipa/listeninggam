package ar.com.westsoft.listening.screen.dictationgame.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.engine.DictationGameHeader
import ar.com.westsoft.listening.domain.dictationgame.repository.GetDictationGameLabels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDictationGameViewModel @Inject constructor(
    private val getDictationGameLabels: GetDictationGameLabels
) : ViewModel() {

    private val _games = MutableStateFlow<List<DictationGameHeader>>(emptyList())
    val games = _games.asStateFlow()

    init{
        viewModelScope.launch {
            _games.emit(getDictationGameLabels())
        }
    }
}
package ar.com.westsoft.listening.screen.dictationgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.engine.DictationGameLabel
import ar.com.westsoft.listening.domain.dictationgame.GetDictationGameLabels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDictationGameViewModel @Inject constructor(
    private val getDictationGameLabels: GetDictationGameLabels
) : ViewModel() {

    private val _games = MutableStateFlow<List<DictationGameLabel>>(emptyList())
    val games = _games.asStateFlow()

    init{
        viewModelScope.launch {
            _games.emit(getDictationGameLabels())
        }
    }
}
package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.input.key.KeyEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.dictionary.repository.DictionaryManager
import ar.com.westsoft.listening.dictionary.screen.DictionaryScreenState
import ar.com.westsoft.listening.domain.dictationgame.engine.KeyEventUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.GetSpeedLevelUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.StoreSpeedLevelUseCase
import ar.com.westsoft.listening.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameMainViewModel @Inject constructor(
    private val dictationGame: DictationGame,
    private val keyEventUseCase: KeyEventUseCase,
    private val getSpeedLevelUseCase: GetSpeedLevelUseCase,
    private val storeSpeedLevelUseCase: StoreSpeedLevelUseCase,
    private val dictionaryManager: DictionaryManager
) : ViewModel() {

    private val isMutableShowingPreference = MutableStateFlow(false)
    val isShowingPreference = isMutableShowingPreference as StateFlow<Boolean>

    private val isMutableShowingDictionary = MutableStateFlow(false)
    val isShowingDictionary = isMutableShowingDictionary as StateFlow<Boolean>

    private val _dictionaryStack = MutableStateFlow<List<DictionaryScreenState>>(emptyList())
    val dictionaryStack = _dictionaryStack.asStateFlow()

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

    fun onDictButtonClicked() {
        viewModelScope.launch {
            val word = dictationGame.getCurrentWord() ?: return@launch
            pushDictionaryScreen(word)
        }
    }

    fun onWordSelected(word: String) {
        viewModelScope.launch {
            pushDictionaryScreen(word)
        }
    }

    private suspend fun pushDictionaryScreen(word: String) {
        val currentState = _dictionaryStack.value
        val newState = DictionaryScreenState(word, emptyList(), "Searching definition for $word...")
        _dictionaryStack.value = currentState + newState
        isMutableShowingDictionary.value = true

        val result = dictionaryManager.getDefinition(word)
        val errorMsg = if (result.isEmpty()) "No definitions found or error fetching data." else null

        val updatedList = _dictionaryStack.value.toMutableList()
        if (updatedList.isNotEmpty()) {
            val lastIndex = updatedList.size - 1
            updatedList[lastIndex] = DictionaryScreenState(word, result, errorMsg)
            _dictionaryStack.value = updatedList
        }
    }

    fun onDictionaryClosed() {
        viewModelScope.launch {
            val stack = _dictionaryStack.value
            if (stack.size > 1) {
                _dictionaryStack.value = stack.dropLast(1)
            } else {
                _dictionaryStack.value = emptyList()
                isMutableShowingDictionary.emit(false)
            }
        }
    }

    fun onKeyEvent(keyEvent: KeyEvent) {
        viewModelScope.launch {
            keyEventUseCase(keyEvent)
        }
    }

    fun setSpeedLevel(speedLevel: SpeedLevelPreference) {
        viewModelScope.launch {
            storeSpeedLevelUseCase(speedLevel)
        }
    }

    val speedLevelState = getSpeedLevelUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SettingsField(Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT, false)
    )

    val resetSignal = dictationGame.resetSignal
}

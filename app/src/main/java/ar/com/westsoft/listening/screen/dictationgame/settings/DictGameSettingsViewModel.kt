package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.domain.dictationgame.settings.SetColumnPerPageUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordAfterCursorUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordBeforeCursorUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetSpeechRateUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetSpeedLevelUseCase
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.GetColumnPerPageUseCase
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.GetReadWordAfterCursorUseCase
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.GetReadWordBeforeCursorUseCase
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.GetSpeechRateUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.GetSpeedLevelUseCase
import ar.com.westsoft.listening.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameSettingsViewModel @Inject constructor(
    private val setReadWordAfterCursorUseCase: SetReadWordAfterCursorUseCase,
    private val setReadWordBeforeCursorUseCase: SetReadWordBeforeCursorUseCase,
    private val setSpeechRateUseCase: SetSpeechRateUseCase,
    private val setSpeedLevelUseCase: SetSpeedLevelUseCase,
    private val setColumnPerPageUseCase: SetColumnPerPageUseCase,
    private val getReadWordBeforeCursorUseCase: GetReadWordBeforeCursorUseCase,
    private val getReadWordAfterCursorUseCase: GetReadWordAfterCursorUseCase,
    private val getColumnPerPageUseCase: GetColumnPerPageUseCase,
    private val getSpeechRateUseCase: GetSpeechRateUseCase,
    private val getSpeedLevelUseCase: GetSpeedLevelUseCase
) : ViewModel() {

    private val handleWordBeforeCursor = HandleTextFieldValue(
        preferencesKey = PreferencesKey.ReadWordBeforeCursor,
        getFlow = { getReadWordBeforeCursorUseCase() },
        saveValue = { setReadWordBeforeCursorUseCase(it) },
        scope = viewModelScope
    )

    fun getWordBeforeCursorFlow() = handleWordBeforeCursor.outputFlow

    fun setReadWordBeforeCursor(textFieldValue: TextFieldValue) {
        handleWordBeforeCursor.saveValue(textFieldValue)
    }

    private val handleWordAfterCursor = HandleTextFieldValue(
        preferencesKey = PreferencesKey.ReadWordAfterCursor,
        getFlow = { getReadWordAfterCursorUseCase() },
        saveValue = { setReadWordAfterCursorUseCase(it) },
        scope = viewModelScope
    )

    fun getWordAfterCursorFlow() = handleWordAfterCursor.outputFlow

    fun onReadWordAfterCursorChanged(textFieldValue: TextFieldValue) {
        handleWordAfterCursor.saveValue(textFieldValue)
    }

    private val handleSpeechRate = HandleTextFieldValue(
        preferencesKey = PreferencesKey.SpeechRatePercentage,
        getFlow = { getSpeechRateUseCase() },
        saveValue = { setSpeechRateUseCase(it) },
        scope = viewModelScope
    )

    fun getSpeechRateFlow() = handleSpeechRate.outputFlow

    fun onSpeechRateChanged(textFieldValue: TextFieldValue) {
        handleSpeechRate.saveValue(textFieldValue)
    }

    val speedLevelStateFlow = getSpeedLevelUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = SettingsField(Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT, false)
        )

    fun onSpeedLevelChanged(speedLevel: SpeedLevelPreference) {
        viewModelScope.launch {
            setSpeedLevelUseCase.invoke(speedLevel)
        }
    }

    private val handleColumnPerPageUseCase = HandleTextFieldValue(
        preferencesKey = PreferencesKey.ColumnPerPage,
        getFlow = { getColumnPerPageUseCase() },
        saveValue = { setColumnPerPageUseCase(it) },
        scope = viewModelScope
    )

    fun getColumnPerPageStateFlow() = handleColumnPerPageUseCase.outputFlow

    fun onColumnPerPageChanged(textFieldValue: TextFieldValue) {
        handleColumnPerPageUseCase.saveValue(textFieldValue)
    }
}

fun TextFieldValue.updateText(value: AnnotatedString): TextFieldValue =
    this.copy(annotatedString = value)

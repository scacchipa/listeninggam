package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.repository.toAnnotatedString
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameSettingsViewModel @Inject constructor(
    private val setReadWordAfterCursorUseCase: SetReadWordAfterCursorUseCase,
    private val setReadWordBeforeCursorUseCase: SetReadWordBeforeCursorUseCase,
    private val setSpeechRateUseCase: SetSpeechRateUseCase,
    private val setSpeedLevelUseCase: SetSpeedLevelUseCase,
    private val getReadWordBeforeCursorUseCase: GetReadWordBeforeCursorUseCase,
    private val getReadWordAfterCursorUseCase: GetReadWordAfterCursorUseCase,
    private val getColumnPerPageUseCase: GetColumnPerPageUseCase,
    private val getSpeechRateUseCase: GetSpeechRateUseCase,
    private val getSpeedLevelUseCase: GetSpeedLevelUseCase,
    private val setColumnPerPageUseCase: SetColumnPerPageUseCase
) : ViewModel() {

    private val handleWordBeforeCursor = HandleTextFieldValue<Int>(
        defaultValue = Constants.PREFERENCES_KEY_READ_WORD_BEFORE_CURSOR_DEFAULT,
        getFlow = { getReadWordBeforeCursorUseCase() },
        saveValue = { setReadWordBeforeCursorUseCase(it) },
        scope = viewModelScope
    )

    fun getWordBeforeCursorFlow() = handleWordBeforeCursor.outputFlow

    fun setReadWordBeforeCursor(textFieldValue: TextFieldValue) {
        handleWordBeforeCursor.saveValue(textFieldValue)
    }

    private val handleWordAfterCursor = HandleTextFieldValue(
        defaultValue = Constants.PREFERENCES_KEY_READ_WORD_AFTER_CURSOR_DEFAULT,
        getFlow = { getReadWordAfterCursorUseCase() },
        saveValue = { setReadWordAfterCursorUseCase(it) },
        scope = viewModelScope
    )

    fun getWordAfterCursorFlow() = handleWordAfterCursor.outputFlow

    fun setReadWordAfterCursor(textFieldValue: TextFieldValue) {
        handleWordAfterCursor.saveValue(textFieldValue)
    }

    private val handleSpeechRate = HandleTextFieldValue(
        defaultValue = Constants.PREFERENCES_KEY_SPEECH_RATE_PERCENTAGE_DEFAULT,
        getFlow = { getSpeechRateUseCase() },
        saveValue = { setSpeechRateUseCase(it) },
        scope = viewModelScope
    )

    fun getSpeechRateFlow() = handleSpeechRate.outputFlow

    fun setSpeechRate(textFieldValue: TextFieldValue) {
        handleSpeechRate.saveValue(textFieldValue)
    }

    val speedLevelStateFlow = getSpeedLevelUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Constants.PREFERENCES_KEY_SPEED_LEVEL_DEFAULT
        )

    fun setSpeedLevel(speedLevel: SpeedLevelPreference) {
        viewModelScope.launch {
            setSpeedLevelUseCase.invoke(speedLevel)
        }
    }

    private val handleColumnPerPageUseCase = HandleTextFieldValue(
        defaultValue = Constants.PREFERENCES_KEY_COLUMN_PER_PAGE_DEFAULT,
        getFlow = { getColumnPerPageUseCase() },
        saveValue = { setColumnPerPageUseCase(it) },
        scope = viewModelScope
    )

    fun getColumnPerPageStateFlow() = handleColumnPerPageUseCase.outputFlow

    fun setColumnPerPage(textFieldValue: TextFieldValue) {
        handleColumnPerPageUseCase.saveValue(textFieldValue)
    }
}

fun <T : Any> TextFieldValue.updateValue(value: SettingsField<T>): TextFieldValue =
    this.copy(annotatedString = value.toAnnotatedString())

fun TextFieldValue.updateText(value: AnnotatedString): TextFieldValue =
    this.copy(annotatedString = value)


fun String.toAnnotatedString(wasSaved: Boolean) = buildAnnotatedString {
    if (!wasSaved) {
        pushStyle(SpanStyle(color = Color.Red))
    }
    append(this@toAnnotatedString)
}

class HandleTextFieldValue<T>(
    defaultValue: T,
    getFlow: () -> Flow<T>,
    private val saveValue: suspend (String) -> Boolean,
    private val scope: CoroutineScope
) {

    private var retainTextFieldValue = TextFieldValue()

    private val errorStateFlow = MutableStateFlow(defaultValue.toString())

    val outputFlow = listOf(
        errorStateFlow
            .map { it.toAnnotatedString(wasSaved = false) },
        getFlow()
            .map { it.toString().toAnnotatedString(wasSaved = true) }
    )
        .merge()
        .map { retainTextFieldValue.updateText(it) }
        .onEach { retainTextFieldValue = it }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = retainTextFieldValue.updateValue(
                SettingsField(PreferencesKey.ReadWordBeforeCursor.defaultValue, false)
            )
        )

    fun saveValue(textFieldValue: TextFieldValue) {
        scope.launch {
            retainTextFieldValue = retainTextFieldValue
                .updateText(textFieldValue.text.toAnnotatedString(wasSaved = false))

            if (!saveValue(textFieldValue.text)) {
                errorStateFlow.emit(textFieldValue.text)
            }
        }
    }

}
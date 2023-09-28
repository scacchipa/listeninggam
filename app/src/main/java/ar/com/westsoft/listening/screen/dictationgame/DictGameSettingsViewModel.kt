package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.SettingsField
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.GetDictSettingFlowUseCase
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordAfterCursorUseCase
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordBeforeCursorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictGameSettingsViewModel @Inject constructor(
    private val setReadWordAfterCursorUseCase: SetReadWordAfterCursorUseCase,
    private val setReadWordBeforeCursorUseCase: SetReadWordBeforeCursorUseCase,
    private val getDictSettingFlowUseCase: GetDictSettingFlowUseCase
) : ViewModel() {

    private val localTextFieldValueMutableStateFlow =
        MutableStateFlow(DictGameScreenSettingsState())

    val screenStateFlow = listOf(
        localTextFieldValueMutableStateFlow,
        getDictSettingFlowUseCase().map {
            val localState = localTextFieldValueMutableStateFlow.value
            localState.copy(
                readWordAfterCursorTextFieldValue = updateTextViewValue(
                    previousValue = localState.readWordAfterCursorTextFieldValue,
                    value = it.readWordAfterCursor
                ),
                readWordBeforeCursorTextFieldValue = updateTextViewValue(
                    previousValue = localState.readWordBeforeCursorTextFieldValue,
                    value = it.readWordBeforeCursor
                )
            )
        }

    ).merge().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DictGameScreenSettingsState()
    )

    private fun getScreenSettingsFlow() = getDictSettingFlowUseCase()

    private fun updateTextViewValue(
        previousValue: TextFieldValue,
        value: SettingsField<String>
    ): TextFieldValue = previousValue.copy(
        annotatedString = buildAnnotatedString {
            if (!value.wasSaved) {
                pushStyle(SpanStyle(color = Color.Red))
            }
            append(value.value)
        })

    fun setReadWordAfterCursor(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            val state = screenStateFlow.first().copy(
                readWordAfterCursorTextFieldValue = textFieldValue
            )

            localTextFieldValueMutableStateFlow.emit(state)
            setReadWordAfterCursorUseCase(textFieldValue.text)
        }
    }

    fun setReadWordBeforeCursor(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            val state = screenStateFlow.first().copy(
                readWordBeforeCursorTextFieldValue = textFieldValue
            )
            localTextFieldValueMutableStateFlow.emit(state)
            setReadWordBeforeCursorUseCase(textFieldValue.text)
        }
    }
}

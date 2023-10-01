package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.repository.toAnnotatedString
import ar.com.westsoft.listening.domain.dictationgame.settings.GetDictSettingFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordAfterCursorUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetReadWordBeforeCursorUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.SetSpeechRateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DictGameSettingsViewModel @Inject constructor(
    private val setReadWordAfterCursorUseCase: SetReadWordAfterCursorUseCase,
    private val setReadWordBeforeCursorUseCase: SetReadWordBeforeCursorUseCase,
    private val setSpeechRateUseCase: SetSpeechRateUseCase,
    private val getSettingFlowUseCase: GetDictSettingFlowUseCase
) : ViewModel() {

    private val directStateFlow = MutableStateFlow(
        runBlocking { getSettingFlowUseCase().first().toScreenSettingsState() }
    )

    val screenStateFlow = listOf(
        directStateFlow,
        getSettingFlowUseCase().map {
            directStateFlow.value.run {
                DictGameScreenSettingsState(
                    readWordAfterCursor = updateTextViewValue(
                        readWordAfterCursor,
                        it.readWordAfterCursor
                    ),
                    readWordBeforeCursor = updateTextViewValue(
                        readWordBeforeCursor,
                        it.readWordBeforeCursor
                    ),
                    speechRate = updateTextViewValue(
                        speechRate,
                        it.speechRatePercentage
                    )
                )
            }
        }
    ).merge().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = runBlocking { getSettingFlowUseCase().first().toScreenSettingsState() }
    )

    private fun updateTextViewValue(
        previousValue: TextFieldValue,
        value: SettingsField
    ): TextFieldValue = previousValue.copy(
        annotatedString = value.toAnnotatedString()
    )

    fun setReadWordAfterCursor(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            val state = screenStateFlow.first().copy(
                readWordAfterCursor = textFieldValue
            )

            directStateFlow.emit(state)
            setReadWordAfterCursorUseCase(textFieldValue.text)
        }
    }

    fun setReadWordBeforeCursor(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            val state = screenStateFlow.first().copy(
                readWordBeforeCursor = textFieldValue
            )
            directStateFlow.emit(state)
            setReadWordBeforeCursorUseCase(textFieldValue.text)
        }
    }

    fun setSpeechRate(textFieldValue: TextFieldValue) {
        viewModelScope.launch {
            val state = screenStateFlow.first().copy(
                speechRate = textFieldValue
            )
            directStateFlow.emit(state)
            setSpeechRateUseCase(textFieldValue.text)
        }
    }
}

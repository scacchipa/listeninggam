package ar.com.westsoft.listening.screen.dictationgame.settings

import androidx.compose.ui.text.input.TextFieldValue
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.repository.toAnnotatedString
import ar.com.westsoft.listening.util.toAnnotatedString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HandleTextFieldValue<T>(
    preferencesKey: PreferencesKey<T>,
    getFlow: () -> Flow<SettingsField<*>>,
    private val saveValue: suspend (String) -> Boolean,
    private val scope: CoroutineScope
) {
    private var retainTextFieldValue = TextFieldValue()
    private val loopbackStateFlow = MutableStateFlow(
        SettingsField(preferencesKey.defaultValue.toString(), false)
    )

    val outputFlow = listOf(
        loopbackStateFlow
            .map { it.toAnnotatedString() },
        getFlow()
            .map { it.toAnnotatedString() }
    )
        .merge()
        .map { retainTextFieldValue.updateText(it) }
        .onEach { retainTextFieldValue = it }
        .stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = retainTextFieldValue
                .updateText(preferencesKey.defaultValue.toString().toAnnotatedString(false))
        )

    fun saveValue(textFieldValue: TextFieldValue) {
        scope.launch {
            retainTextFieldValue = textFieldValue
            loopbackStateFlow.emit(SettingsField(textFieldValue.text, false))

            if (saveValue(textFieldValue.text)) {
                loopbackStateFlow.emit(SettingsField(textFieldValue.text, true))
            }
        }
    }
}

package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.DictGameSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictSettingsRepository @Inject constructor(
    private val dictSettingsDataStore: DictSettingsDataStore
) {

    private var settingsStateFlow = MutableStateFlow(
        DictGameSetting(
            readWordAfterCursor = SettingsField("", false),
            readWordBeforeCursor = SettingsField("", false)
        )
    )

    fun getDictGameSettingFlow() = settingsStateFlow as StateFlow<DictGameSetting>

    suspend fun setReadWordAfterCursor(value: String) {
        val number = value.toIntOrNull()
        val oldState = settingsStateFlow.value
        if (number in 1..200) {
            settingsStateFlow.emit(
                oldState.copy(
                    readWordAfterCursor = SettingsField(value, true)
                )
            )
            dictSettingsDataStore.setReadWordAfterCursor(number ?: 0)
            return
        }
        settingsStateFlow.emit(
            oldState.copy(
                readWordAfterCursor = SettingsField(value, false)
            )
        )
    }

    suspend fun setReadWordBeforeCursor(value: String) {
        val number = value.toIntOrNull()
        val oldState = settingsStateFlow.value
        if (number in 0..10) {
            settingsStateFlow.emit(
                oldState.copy(
                    readWordBeforeCursor = SettingsField(value, true)
                )
            )
            dictSettingsDataStore.setReadWordBeforeCursor(number ?: 0)
            return
        }
        settingsStateFlow.emit(
            oldState.copy(
                readWordBeforeCursor = SettingsField(value, false)
            )
        )
    }
}

data class SettingsField<T>(
    val value: T,
    val wasSaved: Boolean
)

package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameSettings
import ar.com.westsoft.listening.data.datasource.toSetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dictSettingsDataStore: DictSettingsDataStore
) {
    private var loopbackSettingsStateFlow = MutableStateFlow(
        DictGameSettings(
            readWordAfterCursor = SettingsField("", false),
            readWordBeforeCursor = SettingsField("", false)
        )
    )

    private fun getStoredSettings() = dictSettingsDataStore
        .getDictGameSettingsDSOFlow()
        .map { it.toSetting() }

    fun getDictGameSettingFlow() = listOf(
        loopbackSettingsStateFlow,
        getStoredSettings()
    ).merge()

    suspend fun setReadWordAfterCursor(value: String) {
        val number = value.toIntOrNull()
        val oldState = getStoredSettings().first()
        if (number in 1..200) {
            loopbackSettingsStateFlow.emit(
                oldState.copy(
                    readWordAfterCursor = SettingsField(value, true)
                )
            )
            dictSettingsDataStore.setReadWordAfterCursor(number ?: 0)
            return
        }
        loopbackSettingsStateFlow.emit(
            oldState.copy(
                readWordAfterCursor = SettingsField(value, false)
            )
        )
    }

    suspend fun setReadWordBeforeCursor(value: String) {
        val number = value.toIntOrNull()
        val oldState = getStoredSettings().first()
        if (number in 0..10) {
            loopbackSettingsStateFlow.emit(
                oldState.copy(
                    readWordBeforeCursor = SettingsField(value, true)
                )
            )
            dictSettingsDataStore.setReadWordBeforeCursor(number ?: 0)
            return
        }
        loopbackSettingsStateFlow.emit(
            oldState.copy(
                readWordBeforeCursor = SettingsField(value, false)
            )
        )
    }
}

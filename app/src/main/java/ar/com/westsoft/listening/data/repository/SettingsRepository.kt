package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.datasource.toSetting
import ar.com.westsoft.listening.domain.dictationgame.settings.SpeedLevelPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dictSettingsDataStore: DictSettingsDataStore
) {
    private var loopbackSettingsStateFlow = MutableStateFlow(
        runBlocking { dictSettingsDataStore.getDictGameSettingsDSOFlow().first() }
            .toSetting()
    )

    private fun getStoredSettings() = dictSettingsDataStore
        .getDictGameSettingsDSOFlow()
        .map { it.toSetting() }

    fun getDictGameSettingFlow() = listOf(
        loopbackSettingsStateFlow,
        getStoredSettings()
    ).merge()

    suspend fun setReadWordAfterCursor(value: String) {
        emitStatesAndSave(value, PreferencesKey.ReadWordAfterCursor)
    }

    suspend fun setReadWordBeforeCursor(value: String) {
        emitStatesAndSave(value, PreferencesKey.ReadWordBeforeCursor)
    }

    suspend fun setSpeechRate(value: String) {
        emitStatesAndSave(value, PreferencesKey.SpeechRatePercentage)
    }

    suspend fun setSpeedLevel(value: SpeedLevelPreference) {
        emitStatesAndSave(value.name, PreferencesKey.SpeedLevel)
    }

    suspend fun setColumnPerPage(value: String) {
        emitStatesAndSave(value, PreferencesKey.ColumnPerPage)
    }

    private suspend inline fun <reified T> emitStatesAndSave(
        value: String,
        preferenceKey: PreferencesKey<T>
    ) {
        val number = preferenceKey.convert(value) ?: return
        val oldState = getStoredSettings().first()

        if (preferenceKey.conditionToSave(number)) {
            loopbackSettingsStateFlow.emit(
                oldState.copy(
                    param = preferenceKey,
                    newValue = SettingsField(
                        preferenceKey.convert(value),
                        true
                    )
                )
            )

            dictSettingsDataStore.save(preferenceKey, number)
            return
        }

        loopbackSettingsStateFlow.emit(
            oldState.copy(
                param = preferenceKey,
                newValue = SettingsField(preferenceKey.convert(value), false)
            )
        )
    }
}

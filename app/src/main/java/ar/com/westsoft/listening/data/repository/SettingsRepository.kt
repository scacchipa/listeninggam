package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.datasource.toSetting
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dictSettingsDataStore: DictSettingsDataStore
) {

    private fun getStoredSettings() = dictSettingsDataStore
        .getDictGameSettingsDSOFlow()
        .map { it.toSetting() }

    fun getDictGameSettingFlow() = getStoredSettings()

    fun getReadWordAfterCursor() = getDictGameSettingFlow().map { it.readWordAfterCursor }

    suspend fun setReadWordAfterCursor(value: Int): Boolean  {
        dictSettingsDataStore.save(PreferencesKey.ReadWordAfterCursor,value)
        return true
    }

    fun getReadWordBeforeCursor() = getDictGameSettingFlow().map { it.readWordBeforeCursor }

    suspend fun setReadWordBeforeCursor(value: Int): Boolean {
        dictSettingsDataStore.save(PreferencesKey.ReadWordBeforeCursor, value)
        return true
    }

    fun getSpeechRate() = getDictGameSettingFlow().map {it.speechRatePercentage }

    suspend fun setSpeechRate(value: Float): Boolean {
        dictSettingsDataStore.save(PreferencesKey.SpeechRatePercentage, value)
        return true
    }

    fun getSpeedLevel(): Flow<SpeedLevelPreference> = getDictGameSettingFlow().map { it.speedLevel }

    suspend fun setSpeedLevel(value: SpeedLevelPreference): Boolean {
        dictSettingsDataStore.save(PreferencesKey.SpeedLevel, value.name)
        return true
    }

    fun getColumnPerPage() = getDictGameSettingFlow().map { it.columnPerPage }

    suspend fun setColumnPerPage(value: Int): Boolean {
        dictSettingsDataStore.save(PreferencesKey.ColumnPerPage, value)
        return false
    }
}

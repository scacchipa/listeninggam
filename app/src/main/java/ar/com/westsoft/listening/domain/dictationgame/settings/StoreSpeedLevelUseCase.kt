package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class StoreSpeedLevelUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: SpeedLevelPreference): Boolean {
        return if (PreferencesKey.SpeedLevel.conditionToSave(value.name)) {
            settingsRepository.setSpeedLevel(value)
        } else {
            false
        }
    }
}

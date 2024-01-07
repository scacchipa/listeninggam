package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import javax.inject.Inject

class SetSpeedLevelUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: SpeedLevelPreference) {
        settingsRepository.setSpeedLevel(value)
    }
}

package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpeedLevelUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<SpeedLevelPreference> = repository.getSpeedLevel()
}

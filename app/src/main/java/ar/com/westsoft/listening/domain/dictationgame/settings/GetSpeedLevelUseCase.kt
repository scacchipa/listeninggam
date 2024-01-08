package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSpeedLevelUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke() = repository.getSpeedLevel()
        .map { SettingsField(it, true) }
}

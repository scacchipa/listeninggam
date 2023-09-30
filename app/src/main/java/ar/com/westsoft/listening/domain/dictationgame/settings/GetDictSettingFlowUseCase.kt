package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDictSettingFlowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<DictGameSettings> =
        settingsRepository.getDictGameSettingFlow()
}

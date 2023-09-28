package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.DictSettingsRepository
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.DictGameSetting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDictSettingFlowUseCase @Inject constructor(
    private val dictSettingsRepository: DictSettingsRepository
) {
    operator fun invoke(): Flow<DictGameSetting> =
        dictSettingsRepository.getDictGameSettingFlow()

}

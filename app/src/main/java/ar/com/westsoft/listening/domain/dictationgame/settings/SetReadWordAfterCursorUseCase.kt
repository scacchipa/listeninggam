package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.DictSettingsRepository
import javax.inject.Inject

class SetReadWordAfterCursorUseCase @Inject constructor(
    private val dictSettingsRepository: DictSettingsRepository
) {
    suspend operator fun invoke(value: String) {
        dictSettingsRepository.setReadWordAfterCursor(value)
    }
}
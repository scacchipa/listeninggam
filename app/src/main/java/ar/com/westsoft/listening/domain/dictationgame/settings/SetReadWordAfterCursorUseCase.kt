package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.SettingsRepository
import javax.inject.Inject

class SetReadWordAfterCursorUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: String) {
        settingsRepository.setReadWordAfterCursor(value)
    }
}
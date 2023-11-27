package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.repository.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetColumnPerPageUseCase @Inject constructor(
    val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Int =
        runBlocking { settingsRepository.getDictGameSettingFlow().first().columnPerPage.value }
}

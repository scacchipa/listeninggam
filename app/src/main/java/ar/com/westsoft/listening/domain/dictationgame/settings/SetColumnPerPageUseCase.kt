package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class SetColumnPerPageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: String) {
        settingsRepository.setColumnPerPage(value)
    }
}

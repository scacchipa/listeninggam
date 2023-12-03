package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class GetColumnPerPageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke() = repository.getColumnPerPage()
}

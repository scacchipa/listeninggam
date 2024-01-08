package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.repository.SettingsField
import ar.com.westsoft.listening.data.repository.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetColumnPerPageUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke() = repository.getColumnPerPage()
        .map { SettingsField(it, true) }
}

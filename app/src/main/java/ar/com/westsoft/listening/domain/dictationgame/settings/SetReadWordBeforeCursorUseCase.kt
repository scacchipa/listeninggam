package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class SetReadWordBeforeCursorUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: String): Boolean {
        val number = PreferencesKey.ReadWordBeforeCursor.convert(value) ?: return false

        return if (PreferencesKey.ReadWordBeforeCursor.conditionToSave(number)) {
            settingsRepository.setReadWordBeforeCursor(number)
        } else {
            false
        }

    }
}

package ar.com.westsoft.listening.domain.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.PreferencesKey
import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class StoreSpeechRateUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: String): Boolean {
        val number = PreferencesKey.SpeechRatePercentage.convert(value) ?: return false

        return if (PreferencesKey.SpeechRatePercentage.conditionToSave(number)) {
            settingsRepository.setSpeechRate(number)
        } else {
            return false
        }

    }
}

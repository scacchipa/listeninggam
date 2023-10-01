package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.engine.DictationGame
import ar.com.westsoft.listening.data.repository.SettingsRepository
import javax.inject.Inject

class SpeakOutUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settings: SettingsRepository
) {
    suspend operator fun invoke(offset: Int = 0) {
        dictationGame.speakOut(
            offset,
            // TODO
            wordCount = 6 //settings.getReadWordAfterCursor()
        )
    }
}
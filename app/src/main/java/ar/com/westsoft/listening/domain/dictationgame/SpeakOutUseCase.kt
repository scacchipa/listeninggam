package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGame
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.DictSettingsRepository
import javax.inject.Inject

class SpeakOutUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settings: DictSettingsRepository
) {
    suspend operator fun invoke(offset: Int = 0) {
        dictationGame.speakOut(
            offset,
            // TODO
            wordCount = 6 //settings.getReadWordAfterCursor()
        )
    }
}

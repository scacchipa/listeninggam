package ar.com.westsoft.listening.domain

import ar.com.westsoft.listening.service.DictationGame
import javax.inject.Inject

class SpeakOutUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(offset: Int = 0) {
        dictationGame.speakOut(offset)
    }
}

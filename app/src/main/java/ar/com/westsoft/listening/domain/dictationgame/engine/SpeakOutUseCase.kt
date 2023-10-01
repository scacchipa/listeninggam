package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.engine.DictationGame
import javax.inject.Inject

class SpeakOutUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(offset: Int = 0) {
        dictationGame.speakOut(offset)
    }
}

package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGame
import javax.inject.Inject

class InitializeDictationUseCase @Inject constructor(
    private val dictationGame: DictationGame,
) {
    operator fun invoke() {
        dictationGame.initialize()
    }
}
package ar.com.westsoft.listening.domain

import ar.com.westsoft.listening.service.DictationGame
import javax.inject.Inject

class InitializeDictationUseCase @Inject constructor(
    private val dictationGame: DictationGame,
) {
    operator fun invoke() {
        dictationGame.initialize()
    }
}
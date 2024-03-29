package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import javax.inject.Inject

class SetupDictationUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    suspend operator fun invoke(gui: Long) {
        dictationGame.setup(gui)
    }
}

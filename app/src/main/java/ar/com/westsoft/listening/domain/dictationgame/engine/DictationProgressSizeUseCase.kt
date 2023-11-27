package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import javax.inject.Inject

class DictationProgressSizeUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(): Int? = dictationGame.dictationGameRecord?.dictationProgressList?.size
}

package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGame
import ar.com.westsoft.listening.screen.dictationgame.DictationViewState
import javax.inject.Inject

class FirstViewStateUseCase @Inject constructor(
    private val dictationGame: DictationGame
){
    operator fun invoke(): DictationViewState {
        return dictationGame.getFirstViewState()
    }
}

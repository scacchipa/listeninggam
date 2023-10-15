package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetDictationGameStateFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
) {
    operator fun invoke(scope: CoroutineScope): StateFlow<DictGameState> {
        println("getting DictationGameStateFlow")
        return dictationGame.getDictationGameStateFlow().map { state ->

            if (state.cursorColumn == null)
                state.copy(cursorColumn = 0)
            else
                state

        }.stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = dictationGame.getFirstViewState()
        )
    }
}

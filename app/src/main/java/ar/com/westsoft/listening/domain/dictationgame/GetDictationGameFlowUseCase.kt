package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGame
import ar.com.westsoft.listening.screen.dictationgame.DictationViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetDictationGameFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
) {
    operator fun invoke(scope: CoroutineScope): StateFlow<DictationViewState> {
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
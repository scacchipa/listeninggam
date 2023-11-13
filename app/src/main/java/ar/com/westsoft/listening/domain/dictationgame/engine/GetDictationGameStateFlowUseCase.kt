package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.game.SimpleCursorPos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDictationGameStateFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(): Flow<SimpleCursorPos> {
        return dictationGame.getDictationGameStageFlow.map { stage ->
            if (stage.cursorPos == null)
                stage.copy(cursorPos = 0)
            else
                stage
        }.map { stage ->
            SimpleCursorPos(
                paragraphIdx = stage.paragraphIdx,
                letterPos = stage.cursorPos
            )
        }
    }
}

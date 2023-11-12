package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game.ComplexCursorPos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDictationGameStateFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(): Flow<DictGameState> {
        return dictationGame.getDictationGameStageFlow.map { stage ->
            if (stage.cursorPos == null)
                stage.copy(cursorPos = 0)
            else
                stage
        }.map { stage ->
            var cursorCol: Int? = null
            var cursorRow: Int? = null

            DictGameState(
                pos = stage.cursorPos,
                cursorPos = ComplexCursorPos(
                    paragraphIdx = stage.paragraphIdx,
                    row = cursorRow,
                    column = cursorCol)
            )
        }
    }
}

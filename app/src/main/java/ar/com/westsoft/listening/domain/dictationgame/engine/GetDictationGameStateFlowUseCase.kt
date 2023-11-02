package ar.com.westsoft.listening.domain.dictationgame.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDictationGameStateFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<DictGameState> {
        return dictationGame.getDictationGameStageFlow().map { stage ->
            if (stage.cursorColumn == null)
                stage.copy(cursorColumn = 0)
            else
                stage
        }.map { stage ->
            val settings = settingsRepository.getDictGameSettingFlow().first()

            stage.copy(
                charsToShow = stage.charsToShow.splitInRow(settings.columnPerPage.value)
            )
        }.map { stage ->
            DictGameState(
                paragraphIdx = stage.paragraphIdx,
                pos = stage.cursorColumn,
                textToShow = buildAnnotatedString {
                    append(stage.charsToShow.concatenate())
                    if (stage.paragraphIdx == stage.utterance.utteranceId?.toInt()) {
                        addStyle(
                            style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                            start = stage.utterance.start,
                            end = stage.utterance.end
                        )
                    }
                    stage.cursorColumn?.let { pos ->
                        addStyle(
                            style = SpanStyle(color = Color.Red),
                            start = pos,
                            end = pos + 1
                        )
                    }
                },
                dictationGameRecord = stage.dictationGameRecord
            )
        }
    }
}

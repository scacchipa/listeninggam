package ar.com.westsoft.listening.domain.dictationgame.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.game.SimpleCursorPos
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetFormatTextInRowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val dictationGame: DictationGame
) {
    operator fun invoke(paragraphIdx: Int): AnnotatedString {
        val gameRecord = dictationGame.dictationGameRecord ?: return AnnotatedString("")

        val utterance = dictationGame.gameStageFlow.value.utterance

        val settings = runBlocking { settingsRepository.getDictGameSettingFlow().first() }

        val charsToShow = gameRecord.dictationProgressList[paragraphIdx].progressTxt
            .splitInRow(settings.columnPerPage.value)

        val cursorPos: SimpleCursorPos = dictationGame.cursorPosStateFlow.value

        val textByRow = charsToShow.concatToString()

        return buildAnnotatedString {
            append(textByRow)
            if (paragraphIdx == utterance.utteranceId?.toInt()) {
                addStyle(
                    style = SpanStyle(fontWeight = FontWeight.ExtraBold),
                    start = utterance.start,
                    end = utterance.end
                )
            }
            if (paragraphIdx == cursorPos.paragraphIdx) {
                cursorPos.letterPos?.let {
                    addStyle(
                        style = SpanStyle(color = Color.Red),
                        start = it,
                        end = it + 1
                    )
                }
            }
        }
    }
}

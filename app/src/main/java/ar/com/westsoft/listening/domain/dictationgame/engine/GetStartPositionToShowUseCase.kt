package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game.ComplexCursorPos
import ar.com.westsoft.listening.util.countRow
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetStartPositionToShowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(cursorPos: ComplexCursorPos, numberRowAbove: Int): Pair<Int, Int>? {
        val gameRecord = dictationGame.dictationGameRecord ?: return null

        val progressList = gameRecord.dictationProgressList
        val rowPerParagraph =
            runBlocking { settingsRepository.getDictGameSettingFlow().first() }.columnPerPage.value

        var leftRow = numberRowAbove - (cursorPos.row ?: 0)
        var currentParagraph = (cursorPos.paragraphIdx ?: 0)

        while (leftRow > 0) {
            if (currentParagraph < 1) {
                currentParagraph = 0
                leftRow = 0
                break
            }
            currentParagraph--
            leftRow -= progressList[currentParagraph].progressTxt
                .splitInRow(rowPerParagraph)
                .countRow()
        }

        return Pair(currentParagraph, -leftRow)
    }
}

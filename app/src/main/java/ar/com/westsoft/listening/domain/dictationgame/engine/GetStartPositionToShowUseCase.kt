package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetStartPositionToShowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(cursorParagraph: Int, cursorRow: Int, rewindRow: Int): Pair<Int, Int> {
        val progressList = dictationGame.dictationGameRecord.dictationProgressList
        val rowPerParagraph =
            runBlocking { settingsRepository.getDictGameSettingFlow().first() }.columnPerPage.value

        var leftRow = rewindRow - cursorRow
        var currentParagraph = cursorParagraph

        while (leftRow > 0) {
            if (currentParagraph < 1) {
                currentParagraph = 0
                leftRow = 0
                break
            }
            currentParagraph--
            leftRow -= progressList[currentParagraph].progressTxt.splitInRow(rowPerParagraph)
                .count { it == '\n' } + 1
        }

        return Pair(currentParagraph, -leftRow)
    }
}




package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.game.SimpleCursorPos
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.screen.dictationgame.game.ComplexCursorPos
import ar.com.westsoft.listening.util.calcColumn
import ar.com.westsoft.listening.util.calcRow
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetComplexCursorUseCase @Inject constructor(
    private val dictationGame: DictationGame,
    private val settingsRepository: SettingsRepository
){
    operator fun invoke(simpleCursorPos: SimpleCursorPos): ComplexCursorPos? {
        val gameRecord = dictationGame.dictationGameRecord ?: return null

        val paragraphIdx = simpleCursorPos.paragraphIdx

        val rowPerParagraph =
            runBlocking { settingsRepository.getDictGameSettingFlow().first() }.columnPerPage

        val charsToShow = gameRecord.dictationProgressList[paragraphIdx].progressTxt.splitInRow(rowPerParagraph)

        val letterPos = simpleCursorPos.letterPos ?: 0

        return ComplexCursorPos(
            paragraphIdx = paragraphIdx,
            row = charsToShow.calcRow(letterPos),
            column = charsToShow.calcColumn(letterPos)
        )
    }
}

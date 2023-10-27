package ar.com.westsoft.listening.data.game

data class DictationState(
    var cursorParagraphIdx: Int = 0,
    var cursorLetterPos: Int? = 0
) {

    fun moveNextBlank(gameRecord: DictationGameRecord) : DictationState? {
        return gameRecord
            .dictationProgressList[cursorParagraphIdx]
            .getNextBlank(cursorLetterPos)?.let { nextBlank ->
                DictationState(
                    cursorParagraphIdx = cursorParagraphIdx,
                    cursorLetterPos = nextBlank
                )
            }
    }

    fun moveFirstBlankInNextParagraph(gameRecord: DictationGameRecord) : DictationState {
        val progressList = gameRecord.dictationProgressList

        var blankPosition: Int? = null

        val paragraphWithBlank = (cursorParagraphIdx + 1 until progressList.size).find { idx ->
            blankPosition = progressList[idx].getFirstBlank()
            blankPosition != null
        }

        return DictationState(
            cursorParagraphIdx = paragraphWithBlank ?: cursorParagraphIdx,
            cursorLetterPos = blankPosition
        )
    }
}
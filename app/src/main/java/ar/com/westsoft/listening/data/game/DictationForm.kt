package ar.com.westsoft.listening.data.game

data class DictationForm(
    var cursorParagraphIdx: Int = 0,
    var cursorLetterPos: Int? = 0
) {

    fun moveNextBlank(gameRecord: DictationGameRecord) : DictationForm? {
        return gameRecord
            .dictationProgressList[cursorParagraphIdx]
            .getNextBlank(cursorLetterPos)?.let { nextBlank ->
                DictationForm(
                    cursorParagraphIdx = cursorParagraphIdx,
                    cursorLetterPos = nextBlank
                )
            }
    }

    fun moveFirstBlankInNextParagraph(gameRecord: DictationGameRecord) : DictationForm {
        val progressList = gameRecord.dictationProgressList

        var blankPosition: Int? = null

        val paragraphWithBlank = (cursorParagraphIdx + 1 until progressList.size).find { idx ->
            blankPosition = progressList[idx].getFirstBlank()
            blankPosition != null
        }

        return DictationForm(
            cursorParagraphIdx = paragraphWithBlank ?: cursorParagraphIdx,
            cursorLetterPos = blankPosition
        )
    }
}

package ar.com.westsoft.listening.data.game

data class SimpleCursorPos(
    var paragraphIdx: Int = 0,
    var letterPos: Int? = 0
) {
    fun moveNextBlank(gameRecord: DictationGameRecord) : SimpleCursorPos? {
        return gameRecord
            .dictationProgressList[paragraphIdx]
            .getNextBlank(letterPos)?.let { nextBlank ->
                SimpleCursorPos(
                    paragraphIdx = paragraphIdx,
                    letterPos = nextBlank
                )
            }
    }

    fun moveFirstBlankInNextParagraph(gameRecord: DictationGameRecord) : SimpleCursorPos {
        val progressList = gameRecord.dictationProgressList

        var blankPosition: Int? = null

        val paragraphWithBlank = (paragraphIdx + 1 until progressList.size).find { idx ->
            blankPosition = progressList[idx].getFirstBlank()
            blankPosition != null
        }

        return SimpleCursorPos(
            paragraphIdx = paragraphWithBlank ?: paragraphIdx,
            letterPos = blankPosition
        )
    }
}

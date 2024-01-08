package ar.com.westsoft.listening.data.game

import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleCursorPosTest {

    @Test
    fun subjectMoveNext() {
        assertEquals(SimpleCursorPos(0, 0).moveNextBlank(dictationGameRecord), SimpleCursorPos(0, 1))
        assertEquals(SimpleCursorPos(0, 17).moveNextBlank(dictationGameRecord), SimpleCursorPos(0, 19))
        assertEquals(SimpleCursorPos(0, 18).moveNextBlank(dictationGameRecord), SimpleCursorPos(0, 19))
        assertEquals(SimpleCursorPos(0, 19).moveNextBlank(dictationGameRecord), SimpleCursorPos(0, 20))
        assertEquals(SimpleCursorPos(0, 26).moveNextBlank(dictationGameRecord), SimpleCursorPos(0, 27))
        assertEquals(SimpleCursorPos(0, 27).moveNextBlank(dictationGameRecord), null)
        assertEquals(SimpleCursorPos(1, 0).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 1))
        assertEquals(SimpleCursorPos(1, 10).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 11))
        assertEquals(SimpleCursorPos(1, 11).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 12))
        assertEquals(SimpleCursorPos(1, 22).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 23))
        assertEquals(SimpleCursorPos(1, 23).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 24))
        assertEquals(SimpleCursorPos(1, 24).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 26))
        assertEquals(SimpleCursorPos(1, 25).moveNextBlank(dictationGameRecord), SimpleCursorPos(1, 26))
        assertEquals(SimpleCursorPos(1, 26).moveNextBlank(dictationGameRecord), null)
        assertEquals(SimpleCursorPos(1, 27).moveNextBlank(dictationGameRecord), null)
        assertEquals(SimpleCursorPos(1, 28).moveNextBlank(dictationGameRecord), null)
        assertEquals(SimpleCursorPos(1, 29).moveNextBlank(dictationGameRecord), null)
    }

    @Test
    fun subjectMoveFirstBlankInNextParagraph() {
        assertEquals(SimpleCursorPos(0, 27).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(1, 1))
        assertEquals(SimpleCursorPos(0, 28).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(1, 1))
        assertEquals(SimpleCursorPos(0, 29).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(1, 1))
        assertEquals(SimpleCursorPos(0, 30).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(1, 1))
        assertEquals(SimpleCursorPos(0, 31).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(1, 1))

        assertEquals(SimpleCursorPos(1, 25).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(2, 0))
        assertEquals(SimpleCursorPos(1, 26).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(2, 0))
        assertEquals(SimpleCursorPos(1, 27).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(2, 0))
        assertEquals(SimpleCursorPos(1, 28).moveFirstBlankInNextParagraph(dictationGameRecord), SimpleCursorPos(2, 0))
    }

    private val dictationProgress = listOf(
        "“What did you come for, then?”",
        "“It was to tell you, hadn’t",
        "First char."
    ).mapIndexed { index, string ->
        DictationProgress(
            progressId = index.toLong(),
            originalTxt = string,
            progressTxt = string.map { char ->
                if (char.isLetter() || char.isDigit()) '_'
                else char
            }.toCharArray()
        )
    }

    private val dictationGameRecord = DictationGameRecord(
        gameHeader = DictationGameHeader(),
        dictationProgressList = dictationProgress
    )
}
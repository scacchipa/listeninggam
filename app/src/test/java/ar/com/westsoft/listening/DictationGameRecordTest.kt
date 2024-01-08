package ar.com.westsoft.listening

import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.data.game.DictationProgress
import org.junit.Assert.assertEquals
import org.junit.Test

class DictationGameRecordTest {

    private val subject1 = DictationGameRecord(
        gameHeader = DictationGameHeader(),
        dictationProgressList = listOf(
            DictationProgress(
                progressId = 1,
                originalTxt = "Hello World!",
                progressTxt = "Hello World!".toCharArray()
            ),
            DictationProgress(
                progressId = 1,
                originalTxt = "White black",
                progressTxt = "_____ _____".toCharArray()
            )
        )
    )

    private val subject2 = DictationGameRecord(
        gameHeader = DictationGameHeader(),
        dictationProgressList = listOf(
            DictationProgress(
                progressId = 1,
                originalTxt = "Hello World",
                progressTxt = "_____ _____".toCharArray()
            )
        )
    )

    private val subject3 = DictationGameRecord(
        gameHeader = DictationGameHeader(),
        dictationProgressList = listOf(
            DictationProgress(
                progressId = 1,
                originalTxt = "Hello World",
                progressTxt = "Hello World".toCharArray()
            )
        )
    )

    @Test
    fun getGlobalProgressRate_returnAValue() {
        assertEquals(0.40, subject1.getGlobalProgressRate(), 0.51)
        assertEquals(0.00, subject2.getGlobalProgressRate(), 0.01)
        assertEquals(0.99, subject3.getGlobalProgressRate(), 1.00)
    }
}
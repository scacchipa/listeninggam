package ar.com.westsoft.listening.data.game

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.util.Field
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.hideLetters
import org.junit.Assert.assertEquals
import org.junit.Test

class DictationProgressTest {

    private val text = "“What did don't you come for, then?”"
    private val hideText = "“____ ___ ___'_ ___ ____ ___, ____?”"
    private val firstWordRevealedText = "“What ___ ___'_ ___ ____ ___, ____?”"
    private val secondWordRevealedText = "“____ did ___'_ ___ ____ ___, ____?”"
    private val thirdWordRevealText = "“____ ___ don't ___ ____ ___, ____?”"
    private val sixthWordRevealedText = "“____ ___ ___'_ ___ ____ for, ____?”"
    private val lastWordRevealedText = "“____ ___ ___'_ ___ ____ ___, then?”"
    private val allLetterHidden = "___ ____ ____"
    private val underScoreText = "“What_did_don't you come for, then?”"
    private val hiddenUnderScoreText = "“What_did_don't y__ ____ ___, ____?”"
    private val charArray = hideText.toCharArray()

    @Test
    fun subjectSetLetterProgress_nullPos() {
        val subject = buildDictationProgress1()

        subject.setLetterProgress(null)
        val expect = buildDictationProgress1()

        assertEquals(subject, expect)
    }

    @Test
    fun subjectSetLetterProgress_Pos4() {
        val subject = buildDictationProgress1()

        subject.setLetterProgress(4)
        val expect = buildDictationProgress1()
        expect.progressTxt[4] = 't'

        assertEquals(subject, expect)
    }

    @Test
    fun subjectSetLetterProgress_RevealPosition() {
        val subject = buildDictationProgress1()

        subject.setLetterProgress(0)
        val expect = buildDictationProgress1()

        assertEquals(subject, expect)
    }

    @Test
    fun subjectIsCompleted_returnFalse() {
        val subject = buildDictationProgress1()

        assert(subject.isCompleted().not())
    }

    @Test
    fun subjectIsCompleted_returnTrue() {
        val subject = buildDictationProgress1()

        subject.progressTxt.forEachIndexed { index, _ ->
            subject.progressTxt[index] = subject.originalTxt[index]
        }

        assert(subject.isCompleted())
    }

    @Test
    fun subjectRevealParagraph() {
        val subject = buildDictationProgress1()

        subject.revealParagraph()
        assert(subject.progressTxt.concatenate() == subject.originalTxt)
    }

    @Test
    fun subjectRevelAWord() {
        checkRevelWordPos(0, hideText)
        checkRevelWordPos(1, firstWordRevealedText)
        checkRevelWordPos(2, firstWordRevealedText)
        checkRevelWordPos(3, firstWordRevealedText)
        checkRevelWordPos(4, firstWordRevealedText)
        checkRevelWordPos(5, hideText)
        checkRevelWordPos(6, secondWordRevealedText)
        checkRevelWordPos(7, secondWordRevealedText)
        checkRevelWordPos(8, secondWordRevealedText)
        checkRevelWordPos(9, hideText)
        checkRevelWordPos(10, thirdWordRevealText)
        checkRevelWordPos(11, thirdWordRevealText)
        checkRevelWordPos(12, thirdWordRevealText)
        checkRevelWordPos(13, hideText)
        checkRevelWordPos(14, thirdWordRevealText)
        checkRevelWordPos(25, sixthWordRevealedText)
        checkRevelWordPos(26, sixthWordRevealedText)
        checkRevelWordPos(27, sixthWordRevealedText)
        checkRevelWordPos(28, hideText)
        checkRevelWordPos(30, lastWordRevealedText)
        checkRevelWordPos(31, lastWordRevealedText)
        checkRevelWordPos(32, lastWordRevealedText)
        checkRevelWordPos(33, lastWordRevealedText)
    }

    @Test
    fun subjectGenerateHashCode() {
        val subject = DictationProgress(5, text, text.hideLetters())

        val actual = subject.hashCode()
        val expect = -1426373631

        assertEquals(expect, actual)
    }

    @Test
    fun subjectToEntity_returnAProgressEntity() {
        val subject = DictationProgress(5, text, charArray)

        val actual = subject.toEntity()
        val expect = DictationProgressEntity(
                progressId = 5,
                gameHeaderId = Field.unknown,
                originalTxt = text,
                progressTxt = hideText
            )

        assertEquals(actual, expect)
    }

    @Test
    fun subjectGetFirstBlank() {
        assertEquals(1, DictationProgress(5, text, hideText.toCharArray()).getFirstBlank())
        assertEquals(6, DictationProgress(6, text, firstWordRevealedText.toCharArray()).getFirstBlank())
        assertEquals(0, DictationProgress(5, text, allLetterHidden.toCharArray()).getFirstBlank())
        assertEquals(
            17,
            DictationProgress(5, underScoreText, hiddenUnderScoreText.toCharArray()).getFirstBlank()
        )
    }

    @Test
    fun subjectGetNextBlank() {

        val subject1 = DictationProgress(6, text, secondWordRevealedText.toCharArray())

        assertEquals(1, subject1.getNextBlank(0))
        assertEquals(2, subject1.getNextBlank(1))
        assertEquals(3, subject1.getNextBlank(2))
        assertEquals(4, subject1.getNextBlank(3))
        assertEquals(10, subject1.getNextBlank(4))
        assertEquals(10, subject1.getNextBlank(5))
        assertEquals(14, subject1.getNextBlank(12))
        assertEquals(14, subject1.getNextBlank(13))
        assertEquals(null, subject1.getNextBlank(33))
        assertEquals(null, subject1.getNextBlank(34))

        val subject2 = DictationProgress(5, underScoreText, hiddenUnderScoreText.toCharArray())

        assertEquals(17, subject2.getNextBlank(0))
        assertEquals(17, subject2.getNextBlank(1))
    }

    @Test
    fun subjectGetIdxPreviousBlank() {
        val subject1 = DictationProgress(6, text, secondWordRevealedText.toCharArray())

        assertEquals(12, subject1.getIdxPreviousBlank(14))
        assertEquals(11, subject1.getIdxPreviousBlank(12))
        assertEquals(10, subject1.getIdxPreviousBlank(11))
        assertEquals(4, subject1.getIdxPreviousBlank(10))
        assertEquals(1, subject1.getIdxPreviousBlank(2))
        assertEquals(null, subject1.getIdxPreviousBlank(1))

        val subject2 = DictationProgress(5, underScoreText, hiddenUnderScoreText.toCharArray())

        assertEquals(null, subject2.getIdxPreviousBlank(16))
        assertEquals(null, subject2.getIdxPreviousBlank(17))
    }

    private fun checkRevelWordPos(pos: Int, result: String) {
        val subject = buildDictationProgress1()
        subject.revealWord(pos)
        assert(subject.progressTxt.contentEquals(result.toCharArray()))
    }

    private fun buildDictationProgress1() = DictationProgress(
        progressId = 1,
        originalTxt = text,
        progressTxt = text.hideLetters()
    )
}

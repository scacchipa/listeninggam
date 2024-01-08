package ar.com.westsoft.listening.string

import ar.com.westsoft.listening.util.findLastLetterOnLeft
import ar.com.westsoft.listening.util.firstLetterOfWord
import ar.com.westsoft.listening.util.prevWord
import ar.com.westsoft.listening.util.rewindALetter
import ar.com.westsoft.listening.util.rewindWords
import ar.com.westsoft.listening.util.rewindWordsOrFirst
import org.junit.Assert
import org.junit.Test

class StringExtTest {

    private val txt = "I'm a developer of software."

    @Test
    fun findLastLetterOnLeftTest() {
        Assert.assertEquals(6, txt.findLastLetterOnLeft(10) { it.isLetter() })
        Assert.assertEquals(6, txt.findLastLetterOnLeft(6) { it.isLetter() })
        Assert.assertEquals(0, txt.findLastLetterOnLeft(0) { it.isLetter() })
        Assert.assertEquals(null, txt.findLastLetterOnLeft(5) { it.isLetter() })
    }

    @Test
    fun rewindALetterTest() {
        Assert.assertEquals(9, txt.rewindALetter(10))
        Assert.assertEquals(0, txt.rewindALetter(1))
        Assert.assertEquals(null, txt.rewindALetter(0))
    }
    @Test
    fun firstLetterOfWordTest() {
        Assert.assertEquals(6, txt.firstLetterOfWord(10))
        Assert.assertEquals(6, txt.firstLetterOfWord(6))
        Assert.assertEquals(0, txt.firstLetterOfWord(0))
        Assert.assertEquals(null, txt.firstLetterOfWord(5))
    }

    @Test
    fun prevWordTest() {
        Assert.assertEquals(16, txt.prevWord(27))
        Assert.assertEquals(16, txt.prevWord(26))
        Assert.assertEquals(16, txt.prevWord(24))
        Assert.assertEquals(6, txt.prevWord(16))
        Assert.assertEquals(4, txt.prevWord(6))
        Assert.assertEquals(0, txt.prevWord(4))
        Assert.assertEquals(null, txt.prevWord(2))
        Assert.assertEquals(null, "   Develop".prevWord(7))
        Assert.assertEquals(null, txt.prevWord(0))
    }

    @Test
    fun rewindWordsTest() {
        Assert.assertEquals(6, txt.rewindWords(27, 2))
        Assert.assertEquals(6, txt.rewindWords(26, 2))
        Assert.assertEquals(0, txt.rewindWords(24, 4))
        Assert.assertEquals(4, txt.rewindWords(16, 2))
        Assert.assertEquals(0, txt.rewindWords(6, 2))
        Assert.assertEquals(null, txt.rewindWords(4, 2))
        Assert.assertEquals(null, txt.rewindWords(2, 2))
        Assert.assertEquals(null, "   Develop".rewindWords(7, 2))
        Assert.assertEquals(null, txt.rewindWords(0, 2))
    }

    @Test
    fun rewindWordsOrFirstTest() {
        Assert.assertEquals(6, txt.rewindWordsOrFirst(27, 2))
        Assert.assertEquals(6, txt.rewindWordsOrFirst(26, 2))
        Assert.assertEquals(0, txt.rewindWordsOrFirst(24, 4))
        Assert.assertEquals(4, txt.rewindWordsOrFirst(16, 2))
        Assert.assertEquals(0, txt.rewindWordsOrFirst(6, 2))
        Assert.assertEquals(0, txt.rewindWordsOrFirst(4, 2))
        Assert.assertEquals(0, txt.rewindWordsOrFirst(2, 2))
        Assert.assertEquals(3, "   Develop".rewindWordsOrFirst(7, 2))
        Assert.assertEquals(0, txt.rewindWordsOrFirst(0, 2))
    }
}

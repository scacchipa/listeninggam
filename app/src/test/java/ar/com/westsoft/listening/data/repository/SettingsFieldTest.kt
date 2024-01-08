package ar.com.westsoft.listening.data.repository

import androidx.compose.ui.graphics.Color
import org.junit.Test

class SettingsFieldTest {


    @Test
    fun subjectCreateAnnotatedStringFromSavedInfo() {
        val annotatedString = SettingsField("Test text", false).toAnnotatedString()

        assert(annotatedString.text == "Test text")
        assert(annotatedString.spanStyles.size == 1)
        assert(annotatedString.spanStyles[0].item.color == Color.Red)
        assert(annotatedString.spanStyles[0].start == 0)
        assert(annotatedString.spanStyles[0].end == 9)
    }

    @Test
    fun subjectCreateAnnotatedStringFromNoSavedInfo() {
        val annotatedString = SettingsField("Test text", true).toAnnotatedString()

        assert(annotatedString.text == "Test text")
        assert(annotatedString.spanStyles.isEmpty())
    }


}
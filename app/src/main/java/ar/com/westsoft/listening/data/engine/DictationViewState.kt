package ar.com.westsoft.listening.data.engine

data class DictationViewState(
    val showedParagraphIdx: Int = 0,
    var cursorParagraphNumber: Int = 0,
    var cursorLetterPos: Int = 0
)

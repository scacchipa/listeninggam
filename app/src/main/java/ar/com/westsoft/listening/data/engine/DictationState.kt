package ar.com.westsoft.listening.data.engine

data class DictationState(
    var cursorParagraphIdx: Int = 0,
    var cursorLetterPos: Int? = 0
)

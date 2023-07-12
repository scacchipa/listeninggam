package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.text.AnnotatedString

data class DictationViewState(
    val paragraphIdx: Int,
    val cursorColumn: Int?,
    val textToShow: AnnotatedString
)

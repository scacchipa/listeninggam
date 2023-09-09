package ar.com.westsoft.listening.screen.dictationgame

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.engine.DictationGameRecord

data class DictGameState(
    val paragraphIdx: Int,
    val cursorColumn: Int?,
    val textToShow: AnnotatedString,
    val dictationGameRecord: DictationGameRecord
)

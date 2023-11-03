package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.game.DictationGameRecord

data class DictGameState(
    val paragraphIdx: Int,
    val pos: Int?,
    val cursorCol: Int?,
    val textToShow: AnnotatedString,
    val dictationGameRecord: DictationGameRecord
)

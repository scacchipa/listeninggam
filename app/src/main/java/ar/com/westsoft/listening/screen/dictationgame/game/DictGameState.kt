package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game.CursorPosition

data class DictGameState(
    val pos: Int?,
    val cursorPosition: CursorPosition,
    val rowCount: Int,
    val textToShow: AnnotatedString,
    val dictationGameRecord: DictationGameRecord
)

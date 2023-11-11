package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game.ComplexCursorPos

data class DictGameState(
    val pos: Int?,
    val cursorPos: ComplexCursorPos,
    val rowCount: Int,
    val textToShow: AnnotatedString
)

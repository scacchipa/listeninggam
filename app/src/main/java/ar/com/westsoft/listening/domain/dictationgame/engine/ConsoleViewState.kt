package ar.com.westsoft.listening.domain.dictationgame.engine

import ar.com.westsoft.listening.data.game.SimpleCursorPos
import ar.com.westsoft.listening.data.engine.Utterance

data class ConsoleViewState(
    val simpleCursorPos: SimpleCursorPos,
    val utterance: Utterance?
)

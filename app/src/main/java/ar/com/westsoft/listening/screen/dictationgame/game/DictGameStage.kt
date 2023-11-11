package ar.com.westsoft.listening.screen.dictationgame.game

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.Utterance

data class DictGameStage(
    val paragraphIdx: Int,
    val cursorPos: Int?,
    val utterance: Utterance,
    val charsToShow: CharArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DictGameStage

        if (paragraphIdx != other.paragraphIdx) return false
        if (cursorPos != other.cursorPos) return false
        if (utterance != other.utterance) return false
        if (!charsToShow.contentEquals(other.charsToShow)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = paragraphIdx
        result = 31 * result + (cursorPos ?: 0)
        result = 31 * result + utterance.hashCode()
        result = 31 * result + charsToShow.contentHashCode()
        return result
    }
}

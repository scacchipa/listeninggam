package ar.com.westsoft.listening.screen.dictationgame.game

import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.engine.Utterance

data class DictGameStage(
    val paragraphIdx: Int,
    val cursorColumn: Int?,
    val utterance: Utterance,
    val charsToShow: CharArray,
    val dictationGameRecord: DictationGameRecord
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DictGameStage

        if (paragraphIdx != other.paragraphIdx) return false
        if (cursorColumn != other.cursorColumn) return false
        if (utterance != other.utterance) return false
        if (!charsToShow.contentEquals(other.charsToShow)) return false
        if (dictationGameRecord != other.dictationGameRecord) return false

        return true
    }

    override fun hashCode(): Int {
        var result = paragraphIdx
        result = 31 * result + (cursorColumn ?: 0)
        result = 31 * result + utterance.hashCode()
        result = 31 * result + charsToShow.contentHashCode()
        result = 31 * result + dictationGameRecord.hashCode()
        return result
    }
}

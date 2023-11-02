package ar.com.westsoft.listening.screen.dictationgame.game

import ar.com.westsoft.listening.data.game.DictationGameRecord

data class DictGameForm(
    val paragraphIdx: Int,
    val pos: Int?,
    val charToShow: CharArray,
    val dictationGameRecord: DictationGameRecord
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DictGameForm

        if (paragraphIdx != other.paragraphIdx) return false
        if (pos != other.pos) return false
        if (!charToShow.contentEquals(other.charToShow)) return false
        if (dictationGameRecord != other.dictationGameRecord) return false

        return true
    }

    override fun hashCode(): Int {
        var result = paragraphIdx
        result = 31 * result + (pos ?: 0)
        result = 31 * result + charToShow.contentHashCode()
        result = 31 * result + dictationGameRecord.hashCode()
        return result
    }
}
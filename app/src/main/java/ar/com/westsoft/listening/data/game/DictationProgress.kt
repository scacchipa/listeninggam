package ar.com.westsoft.listening.data.game

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.util.Field
import ar.com.westsoft.listening.util.concatenate
import ar.com.westsoft.listening.util.getIdxFirst
import ar.com.westsoft.listening.util.getIdxNextTo
import ar.com.westsoft.listening.util.getIdxPreviousTo
import ar.com.westsoft.listening.util.hideLetters

data class DictationProgress(
    var progressId: Long?,
    val originalTxt: String = "",
    val progressTxt: CharArray = originalTxt.hideLetters()
) {
    val progressRate: Double =
        originalTxt.zip(progressTxt.concatToString()).count { (originalChar, progressChar) ->
            originalChar != progressChar
        }.toDouble() / originalTxt.count { it.isLetterOrDigit() }
    val isCompleted: Boolean = progressTxt.concatToString() == originalTxt

    fun setLetterProgress(pos: Int?) {
        pos?.let { _pos ->
            revealLetter(_pos)
        }
    }

    fun revealParagraph() {
        if (isCompleted.not()) {
            (progressTxt.indices).forEach { idx ->
                revealLetter(idx)
            }
        }
    }

    fun revealWord(pos: Int) {
        var posx = pos
        while (posx < originalTxt.length
            && originalTxt[posx].isLetterOrDigit()
        ) {
            revealLetter(posx)
            posx++
        }
    }

    private fun revealLetter(pos: Int) {
        progressTxt[pos] = originalTxt[pos]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DictationProgress

        if (originalTxt != other.originalTxt) return false
        if (!progressTxt.contentEquals(other.progressTxt)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originalTxt.hashCode()
        result = 31 * result + progressTxt.contentHashCode()
        return result
    }

    fun toEntity() = DictationProgressEntity(
        progressId = progressId,
        gameHeaderId = Field.unknown,
        originalTxt = originalTxt,
        progressTxt = progressTxt.concatenate()
    )

    fun getFirstBlank(): Int? {
        var pos = progressTxt.getIdxFirst('_')

        while (pos != null && originalTxt[pos] == '_') {
            pos = progressTxt.getIdxNextTo(pos, '_')
        }

        return pos
    }

    fun getNextBlank(idx: Int?): Int? {
        var pos = progressTxt.getIdxNextTo(idx, '_')

        while (pos != null && originalTxt[pos] == '_') {
            pos = progressTxt.getIdxNextTo(pos, '_')
        }

        return pos
    }

    fun getIdxPreviousBlank(idx: Int?): Int? {

        var pos = progressTxt.getIdxPreviousTo(idx, '_')

        while (pos != null && originalTxt[pos] == '_') {
            pos = progressTxt.getIdxPreviousTo(pos, '_')
        }

        return pos
    }
}

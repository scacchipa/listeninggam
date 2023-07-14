package ar.com.westsoft.listening.data.engine

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.util.Field
import ar.com.westsoft.listening.util.concatenate

data class DictationProgress(
    var progressId: Long?,
    val originalTxt: String = "",
    val progressTxt: CharArray = getInitialProgressText(originalTxt)
) {

    fun getProgress(): CharArray {
        return progressTxt
    }

    fun setLetterProgress(pos: Int) {
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

    companion object {
        fun getInitialProgressText(origin: String): CharArray {
            val mappedOrigin = origin.map { char ->
                if (char.isLetter() || char.isDigit()) '_'
                else char
            }.toCharArray()
            println(mappedOrigin.concatenate())
            return mappedOrigin
        }
    }
}

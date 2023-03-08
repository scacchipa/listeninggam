package ar.com.westsoft.listening.data.engine

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class DictationProgressEntity(
    @ColumnInfo(name = "original_text")
    val originalTxt: String = "",

    @ColumnInfo(name = "progression_text")
    val progressTxt: CharArray = getInitialProgressText(originalTxt)
) {

    fun getProgress(): CharArray {
        return progressTxt
    }

    fun setLetterProgress(pos: Int) {
        progressTxt[pos] = originalTxt[pos]
    }

    fun getAllText(): String {
        return originalTxt
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DictationProgressEntity

        if (originalTxt != other.originalTxt) return false
        if (!progressTxt.contentEquals(other.progressTxt)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originalTxt.hashCode()
        result = 31 * result + progressTxt.contentHashCode()
        return result
    }

    companion object {
        fun getInitialProgressText(origin: String): CharArray {
            val mappedOrigin = origin.map { char ->
                if (char.isLetter() || char.isDigit()) '_'
                else char
            }.toCharArray()
            println(mappedOrigin.joinToString(""))
            return mappedOrigin
        }
    }
}



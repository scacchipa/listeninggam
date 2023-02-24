package ar.com.westsoft.listening.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictationProgress @Inject constructor() {

    private var progress: CharArray = charArrayOf()
    private var originTxt: String = ""

    fun getProgress() : CharArray {
        return progress
    }

    fun setLetterProgress(pos: Int) {
        progress[pos] = originTxt[pos]
    }

    fun getAllText(): String {
        return originTxt
    }

    fun initProgress(origin: String) {
        originTxt = origin
        progress = origin.map { char ->
            if (char.isLetter() || char.isDigit()) '_'
            else char
        }.toCharArray()
    }
}
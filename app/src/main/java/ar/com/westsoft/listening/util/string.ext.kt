package ar.com.westsoft.listening.util

fun String.takeWords(count: Int?): String {
    return count?.let { _count ->

        val stringBuilder = StringBuilder()
        var pos = 0
        var words = 0
        var foundWord = false

        while (words < _count && pos < this.length) {
            stringBuilder.append(this[pos])
            if (this[pos].isLetterOrDigit()) {
                foundWord = true
            } else {
                if (foundWord) words += 1

                foundWord = false

            }
            pos += 1
        }

        if (stringBuilder.lastOrNull()?.isLetterOrDigit()?.not() == true) {
            stringBuilder.deleteCharAt(stringBuilder.count() - 1)
        }

        stringBuilder.toString()

    } ?: this
}

fun String.rewindWordsOrFirst(offset: Int, rewindWordCount: Int): Int? {
    var pos = firstLetterOfWord(offset) ?: return null

    repeat(rewindWordCount) {
        pos = prevWord(pos) ?: return pos
    }

    return pos
}

fun String.rewindWords(offset: Int, rewindWordCount: Int): Int? {
    var pos = firstLetterOfWord(offset) ?: return null

    repeat(rewindWordCount) {
        pos = prevWord(pos) ?: return null
    }

    return pos
}


fun String.prevWord(pos: Int): Int? {
    var idx = firstLetterOfWord(pos) ?: return null
    idx = rewindALetter(idx) ?: return null
    idx = findLastLetterOnLeft(idx) { it.isLetter().not() } ?: return null
    idx = rewindALetter(idx) ?: return null
    idx = firstLetterOfWord(idx) ?: return null

    return idx
}

fun String.firstLetterOfWord(pos: Int): Int? =
    this.findLastLetterOnLeft(pos) {
        it.isLetter()
    }

fun String.rewindALetter(pos: Int): Int? = if (pos > 0) pos - 1 else null

fun String.findLastLetterOnLeft(pos: Int, condition: (Char) -> Boolean): Int? {
    if (isEmpty()) return null
    var last = if (condition(this[pos])) pos else {
        return null
    }

    while (last > 0 && condition(this[last - 1])) {
        last--
    }

    return last
}

fun String.hideLetters(): CharArray = this.map { char ->
    if (char.isLetter() || char.isDigit()) '_'
    else char
}.toCharArray()

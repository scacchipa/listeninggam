package ar.com.westsoft.listening.util

fun String.takeWords(count: Int?): String {
    return count?.let {_count ->

        val stringBuilder = StringBuilder()
        var pos = 0
        var words = 0
        var foundWord = false

        while (words < _count && pos < this.length) {
            stringBuilder.append(this[pos])
            if (this[pos].isLetterOrDigit()) {
                foundWord = true
            }
            else {
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

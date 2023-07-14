package ar.com.westsoft.listening.util

fun CharArray.concatenate(): String = this.joinToString("")

fun CharArray.getIdxFirst(char: Char): Int? {
    var pos = 0

    while (pos < this.size) {
        if (this[pos] == char) return pos
        pos += 1
    }

    return null
}

fun CharArray.getIdxNextTo(idx: Int?, char: Char): Int? {
    idx?.let { _idx ->
        var pos = _idx + 1

        while (pos < this.size) {
            if (this[pos] == char) return pos
            pos += 1
        }
    }
    return null
}

fun CharArray.getIdxPreviousTo(idx: Int?, char: Char): Int? {
    idx?.let { _idx ->
        var pos = _idx - 1
        println("pos: $pos")

        while (pos >= 0) {
            if (this[pos] == char) return pos
            pos -= 1
        }
    }
    return null
}

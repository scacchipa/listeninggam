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

fun CharArray.splitInRow(columnPerPage: Int): CharArray {
    val newCharArray = ArrayList<Char>()
    var nextSpacePos: Int = this.getIdxNextTo(0, ' ') ?: this.lastIndex
    var lastNewLinePos = 0

    this.forEachIndexed { pos, char ->
        if (pos == nextSpacePos) {

            nextSpacePos = this.getIdxNextTo(pos, ' ') ?: this.lastIndex

            if (nextSpacePos - lastNewLinePos > columnPerPage) {
                newCharArray.add('\n')
                lastNewLinePos = pos
            } else {
                newCharArray.add(char)
            }
        } else {
            newCharArray.add(char)
        }

    }
    return newCharArray.toCharArray()
}

fun CharArray.findLastCrIdxBefore(pos: Int): Int? = (0..pos).findLast { this[it] == '\n' }

fun CharArray.countCrBefore(pos: Int): Int = (0..pos).count { this[it] == '\n' }

fun CharArray.countRow(): Int = count { it == '\n' } + 1

fun CharArray.calcRow(letterPos: Int) =
    if (this.isEmpty()) {
        0
    } else {
        this.countCrBefore(letterPos)
    }

fun CharArray.calcColumn(letterPos: Int): Int =
    if (this.isEmpty()) {
        0
    } else {
        letterPos - (findLastCrIdxBefore(letterPos) ?: 0)
    }

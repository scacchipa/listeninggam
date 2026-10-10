package ar.com.scacchipa.exts

import org.w3c.dom.Node

fun String.collapseWhitespace(): String =
    this.replace(Regex("\\s+"), " ").trim()

fun String.repairWordSpacing(): String {
    return Regex("\\s+").replace(this) { matchResult ->
        val range = matchResult.range
        val start = range.first
        val end = range.last + 1

        val charBefore = if (start > 0) this[start - 1] else return@replace ""
        val charAfter = if (end < this.length) this[end] else return@replace ""

        if (charBefore.isLetter() && charAfter.isLetter()) " " else ""
    }.trim()
}

private fun cleanTextNodes(node: Node) {
    if (node.nodeType == Node.TEXT_NODE) {
        val originalText = node.nodeValue
        if (!originalText.isNullOrBlank()) {
            node.nodeValue = originalText.repairWordSpacing()
        }
    } else {
        val children = node.childNodes
        for (i in 0 until children.length) {
            cleanTextNodes(children.item(i))
        }
    }
}

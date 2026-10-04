package ar.com.scacchipa.exts

import org.w3c.dom.Node
import org.xml.sax.InputSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun String.normalizeWhitespace(): String =
    this.replace(Regex("\\s+"), " ").trim()

fun String.cleanXhtmlWhitespace(): String {
    val regex = Regex("\\s+")
    return regex.replace(this) { matchResult ->
        val range = matchResult.range
        val start = range.first
        val end = range.last + 1

        val charBefore = if (start > 0) this[start - 1] else null
        val charAfter = if (end < this.length) this[end] else null

        if (charBefore != null && charAfter != null && charBefore.isLetter() && charAfter.isLetter()) {
            " "
        } else {
            ""
        }
    }.trim()
}

fun String.cleanXhtmlDocumentString(): String {
    return try {
        val dbFactory = DocumentBuilderFactory.newInstance()
        dbFactory.isNamespaceAware = true
        val dBuilder = dbFactory.newDocumentBuilder()
        
        val doc = dBuilder.parse(InputSource(StringReader(this)))
        cleanTextNodes(doc)
        
        val tf = TransformerFactory.newInstance()
        val transformer = tf.newTransformer()
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8")
        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
        transformer.setOutputProperty(OutputKeys.INDENT, "no")
        
        val writer = StringWriter()
        transformer.transform(DOMSource(doc), StreamResult(writer))
        writer.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        this
    }
}

private fun cleanTextNodes(node: Node) {
    if (node.nodeType == Node.TEXT_NODE) {
        val originalText = node.nodeValue
        if (!originalText.isNullOrBlank()) {
            node.nodeValue = originalText.cleanXhtmlWhitespace()
        }
    } else {
        val children = node.childNodes
        for (i in 0 until children.length) {
            cleanTextNodes(children.item(i))
        }
    }
}

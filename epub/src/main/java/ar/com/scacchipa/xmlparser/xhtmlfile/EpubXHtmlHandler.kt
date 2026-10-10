package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.exts.collapseWhitespace
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlString
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlStyle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTitle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlUnknown
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlText
import org.xml.sax.Attributes
import org.xml.sax.helpers.AttributesImpl
import org.xml.sax.helpers.DefaultHandler
import java.util.Stack

class EpubXHtmlHandler() : DefaultHandler() {

    private val tagStack = Stack<EpubXhtmlTag>()

    private var ePubXHtml = EpubXhtmlHtml(AttributesImpl())
    private val textBuffer = StringBuilder()

    private fun flushTextBuffer() {
        if (textBuffer.isNotEmpty()) {
            val text = textBuffer.toString()
            textBuffer.clear()
            if (text.isNotBlank()) {
                addTextToCurrentTag(text)
            }
        }
    }

    private fun addTextToCurrentTag(text: String) {
        when (val tag = tagStack.peek()) {
            is EpubXhtmlTitle -> tag.text = EpubXhtmlString(value = text)
            is EpubXhtmlContainerTag -> tag.contents.add(EpubXhtmlString(value = text))
            is EpubXhtmlText -> tag.text.add(EpubXhtmlTspan().apply {
                this.text = text.collapseWhitespace()
            })
            is EpubXhtmlTspan -> tag.text = text.collapseWhitespace()
            is EpubXhtmlStyle -> tag.text.add(EpubXhtmlString(value = text, normalize = false))
            else -> println("⚠️ Unknown character container: $text")
        }
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        flushTextBuffer()

        val tag = EpubXhtmlTagsContainer.starterTagElement[qName]?.invoke(
            attributes ?: AttributesImpl(), tagStack
        ) ?: EpubXhtmlUnknown(  AttributesImpl().apply {
                this.addAttribute("Unknown name", "Unknown name", "Unknown", "CDATA", qName)
            })

        tagStack.push(tag)

        if (tag is EpubXhtmlUnknown) {
            println("⚠️ Unknown tag: $qName")
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        val text = String(ch!!, start, length)
        textBuffer.append(text)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        flushTextBuffer()
        EpubXhtmlTagsContainer.enderTagElement[qName]?.invoke(tagStack)
            ?: println("⚠️ Unknown tag in endElement: $qName")
    }

    fun getXhtml() = tagStack.peek() as EpubXhtmlHtml

    fun empty() { tagStack.empty() }
}
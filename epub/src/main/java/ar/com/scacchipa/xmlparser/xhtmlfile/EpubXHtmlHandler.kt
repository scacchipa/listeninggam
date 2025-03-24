package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlString
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTitle
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlUnknown
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell.EpubXhtmlTd
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell.EpubXhtmlTh
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtml
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlA
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBlockquote
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBody
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBr
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlCaption
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlDiv
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH1
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH2
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH3
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH4
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH5
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlH6
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlLi
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlNav
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlOl
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlP
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlSpan
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlStrong
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlUl
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape.EpubXhtmlText
import org.xml.sax.Attributes
import org.xml.sax.helpers.AttributesImpl
import org.xml.sax.helpers.DefaultHandler
import java.util.Stack

class EpubXHtmlHandler() : DefaultHandler() {

    private val tagStack = Stack<EpubXhtmlTag>()

    private var ePubXHtml = EpubXhtmlHtml(AttributesImpl())

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {

        val tag = EpubXhtmlTagsContainer.starterTagElement[qName]?.invoke(attributes ?: AttributesImpl())
            ?: EpubXhtmlUnknown(  AttributesImpl().apply {
                this.addAttribute("Unknown name", "Unknown name", "Unknown", "CDATA", qName)
            })

        tagStack.push(tag)

        if (tag is EpubXhtmlUnknown) {
            println("⚠️ Unknown tag: $qName")
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        val text = String(ch!!, start, length)

        if (text.trim().isNotEmpty()) {
            when (val tag = tagStack.peek()) {
                is EpubXhtmlTitle -> tag.text = EpubXhtmlString(value = text)
                is EpubXhtmlBody -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH1 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH2 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH3 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH4 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH5 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlH6 -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlP -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlSpan -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlDiv -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlA -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlStrong -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtml -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlBr -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlBlockquote -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlUl -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlOl -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlLi -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlTh -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlTd -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlCaption -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlNav -> tag.contents.add(EpubXhtmlString(value = text))
                is EpubXhtmlText -> tag.text.add(EpubXhtmlTspan().apply {
                    this.text = text
                })
                is EpubXhtmlTspan -> tag.text = text

                else -> println("⚠️ Unknown character: $text")
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        EpubXhtmlTagsContainer.enderTagElement[qName]?.invoke(tagStack)
            ?: println("⚠️ Unknown tag in endElement: $qName")
    }

    fun getXhtml() = tagStack.peek() as EpubXhtmlHtml
}
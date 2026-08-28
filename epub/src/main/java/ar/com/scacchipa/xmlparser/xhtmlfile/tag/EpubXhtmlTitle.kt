package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlTitle(attributes: Attributes) : EpubXhtmlTag(attributes) {

    override val tagName: String = "title"

    var text: EpubXhtmlString? = null

    override fun tagWrap(): String {
        return tagWrap(content = text?.tagWrap() ?: "")
    }

    override fun getTextContained(): String {
        return "##" + (text?.getTextContained() ?: "")
    }
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlCol(attributes: Attributes) : EpubXhtmlTag(attributes) {
    override val tagName: String = "col"

    override fun getTextContained(): String = ""
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag
import org.xml.sax.Attributes

class EpubXhtmlBr(attributes: Attributes) : EpubXhtmlContainerTag(attributes) {

    override val tagName: String = "br"

    override fun getTextContained(): String = "\n"
}
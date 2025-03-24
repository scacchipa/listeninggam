package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag
import org.xml.sax.Attributes

class EpubXhtmlH1(attributes: Attributes) : EpubXhtmlContainerTag(attributes) {

    override val tagName: String = "h1"
}

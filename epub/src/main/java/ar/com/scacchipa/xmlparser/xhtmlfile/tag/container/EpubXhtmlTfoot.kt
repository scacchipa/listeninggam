package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import org.xml.sax.Attributes

class EpubXhtmlTfoot(attributes: Attributes) : EpubXhtmlRowContainerTag(attributes) {

    override val tagName: String = "tfoot"

    override val rows = mutableListOf<EpubXhtmlTr>()
}
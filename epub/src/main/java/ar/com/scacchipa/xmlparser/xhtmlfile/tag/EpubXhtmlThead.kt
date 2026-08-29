package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag
import org.xml.sax.Attributes

class EpubXhtmlThead(attributes: Attributes) : EpubXhtmlRowContainerTag(attributes) {

    override val tagName: String = "thead"

    override val rows = mutableListOf<EpubXhtmlTr>()
}
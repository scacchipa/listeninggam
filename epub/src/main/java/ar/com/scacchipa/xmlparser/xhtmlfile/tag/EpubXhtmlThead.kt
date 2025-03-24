package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag
import org.xml.sax.Attributes

class EpubXhtmlThead(attributes: Attributes) : EpubXhtmlRowContainerTag(attributes) {

    override val tagName: String = "head"

    override val rows = mutableListOf<EpubXhtmlTr>()

    override fun toString(): String {
        return "<thead>" +
                rows.fold(StringBuilder()) { acc, elem -> acc.append(elem.toString()) } +
                "/thead"
    }
}
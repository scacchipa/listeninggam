package ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlCell
import org.xml.sax.Attributes

class EpubXhtmlTh(attributes: Attributes) : EpubXhtmlCell(attributes) {

    override val tagName: String = "th"
}
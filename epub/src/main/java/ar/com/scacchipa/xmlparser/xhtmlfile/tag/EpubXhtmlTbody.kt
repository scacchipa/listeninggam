package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag
import org.xml.sax.Attributes

class EpubXhtmlTbody : EpubXhtmlRowContainerTag {

    override val rows: MutableList<EpubXhtmlTr> = mutableListOf()

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override val tagName: String = "body"

    override fun toString(): String {
        return "<tbody>" +
                rows.fold(StringBuilder()) { acc, elem ->
                    acc.append(elem.toString())
                } +
                "/tbody"
    }
}
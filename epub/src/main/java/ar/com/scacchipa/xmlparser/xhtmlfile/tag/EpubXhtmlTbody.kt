package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag

class EpubXhtmlTbody : EpubXhtmlRowContainerTag() {

    override val tagName: String = "body"

    override val rows = mutableListOf<EpubXhtmlTr>()

    override fun toString(): String {
        return "<tbody>" +
                rows.fold(StringBuilder()) { acc, elem ->
                    acc.append(elem.toString())
                } +
                "/tbody"
    }
}
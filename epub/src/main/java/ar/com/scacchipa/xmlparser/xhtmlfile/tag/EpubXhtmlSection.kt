package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag

class EpubXhtmlSection: EpubXhtmlContainerTag() {

    override val tagName: String = "section"

    override fun toString(): String {
        return "<section>" +
                contents.fold(StringBuilder()) { acc, elem -> acc.append(elem) } +
                "</section>"
    }
}
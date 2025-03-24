package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlCell
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlTr : EpubXhtmlTag() {

    override val tagName: String = "tr"

    val cells = mutableListOf<EpubXhtmlCell>()
}
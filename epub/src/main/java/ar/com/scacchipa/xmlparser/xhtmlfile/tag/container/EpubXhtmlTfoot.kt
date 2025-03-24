package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlRowContainerTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr

class EpubXhtmlTfoot : EpubXhtmlRowContainerTag() {

    override val tagName: String = "tfoot"

    override val rows = mutableListOf<EpubXhtmlTr>()
}
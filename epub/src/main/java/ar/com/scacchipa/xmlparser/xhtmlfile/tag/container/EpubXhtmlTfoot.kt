package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlRowContainerTag

class EpubXhtmlTfoot : IEpubXhtmlRowContainerTag {
    override val rows = mutableListOf<EpubXhtmlTr>()
}
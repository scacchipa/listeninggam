package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlRowContainerTag

class EpubXhtmlTbody : IEpubXhtmlRowContainerTag {
    override val rows = mutableListOf<EpubXhtmlTr>()
}
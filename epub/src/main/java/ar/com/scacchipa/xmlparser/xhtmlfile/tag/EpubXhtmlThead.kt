package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlRowContainerTag

class EpubXhtmlThead : IEpubXhtmlRowContainerTag {
    override val rows = mutableListOf<EpubXhtmlTr>()
}
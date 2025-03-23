package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr

interface IEpubXhtmlRowContainerTag : IEpubXhtmlTag {
    val rows: MutableList<EpubXhtmlTr>
}

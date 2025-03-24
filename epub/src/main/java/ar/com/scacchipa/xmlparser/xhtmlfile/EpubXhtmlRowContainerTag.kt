package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr

abstract class EpubXhtmlRowContainerTag : EpubXhtmlTag() {
    abstract val rows: MutableList<EpubXhtmlTr>
}

package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import org.xml.sax.Attributes

abstract class EpubXhtmlRowContainerTag : EpubXhtmlTag {

    abstract val rows: MutableList<EpubXhtmlTr>

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)
}

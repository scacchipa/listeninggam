package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlCell
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlCellContainerTag
import org.xml.sax.Attributes

class EpubXhtmlTr : EpubXhtmlCellContainerTag {

    override val cells: MutableList<EpubXhtmlCell> = mutableListOf()

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override val tagName: String = "tr"
}
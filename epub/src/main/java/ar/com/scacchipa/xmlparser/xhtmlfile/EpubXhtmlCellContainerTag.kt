package ar.com.scacchipa.xmlparser.xhtmlfile

import org.xml.sax.Attributes

abstract class EpubXhtmlCellContainerTag : EpubXhtmlTag {

    abstract val cells: MutableList<EpubXhtmlCell>

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override fun tagWrap(): String {
        return "<$tagName>" +
                cells.fold(StringBuilder()) { acc, elem ->
                    acc.append(elem.tagWrap())
                } +
                "</$tagName>"
    }

    override fun getTextContained(): String = cells.joinToString("") { it.getTextContained() }
}
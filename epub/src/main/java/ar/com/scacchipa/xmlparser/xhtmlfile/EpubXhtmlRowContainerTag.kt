package ar.com.scacchipa.xmlparser.xhtmlfile

import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTr
import org.xml.sax.Attributes

abstract class EpubXhtmlRowContainerTag : EpubXhtmlTag {

    abstract val rows: MutableList<EpubXhtmlTr>

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override fun tagWrap(): String {
        return "<$tagName>" +
                rows.fold(StringBuilder()) { acc, elem ->
                    acc.append(elem.tagWrap())
                } +
                "</$tagName>"
    }

    override fun getTextContained(): String = rows.joinToString("") { it.getTextContained() }
}


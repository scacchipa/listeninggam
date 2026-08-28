package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlColgroup : EpubXhtmlTag {

    override val tagName: String = "colgroup"

    val cols: MutableList<EpubXhtmlCol> = mutableListOf()

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override fun tagWrap(): String {
        return tagWrap(
            content = cols.fold(StringBuilder()) {
                acc, col -> acc.append(col.tagWrap())
            }.toString()
        )
    }

    override fun getTextContained(): String = cols.joinToString("") { it.getTextContained() }
}
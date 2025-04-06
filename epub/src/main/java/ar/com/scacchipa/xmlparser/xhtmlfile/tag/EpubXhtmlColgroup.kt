package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlColgroup(attributes: Attributes) : EpubXhtmlTag(attributes) {

    override val tagName: String = "colgroup"

    val cols: MutableList<EpubXhtmlCol> = mutableListOf()

    override fun tagWrap(): String {
        return tagWrap(
            content = cols.fold(StringBuilder()) {
                acc, col -> acc.append(col.tagWrap())
            }.toString()
        )
    }
}
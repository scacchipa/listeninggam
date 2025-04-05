package ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlColgroup
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTbody
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlThead
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlCaption
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlTfoot
import org.xml.sax.Attributes

class EpubXhtmlTable(attributes: Attributes): EpubXhtmlTag(attributes) {

    override val tagName: String = "table"

    var caption: EpubXhtmlCaption? = null
    var colgroup: EpubXhtmlColgroup? = null
    var thead: EpubXhtmlThead? = null
    var tfoot: EpubXhtmlTfoot? = null
    var tbody: EpubXhtmlTbody? = null

    override fun tagWrap(): String {
        return tagWrap(
            (caption?.tagWrap() ?: "") +
                    (colgroup?.tagWrap() ?: "") +
                    (thead?.tagWrap() ?: "") +
                    (tbody?.tagWrap() ?: "") +
                    (tfoot?.tagWrap() ?: ""))

    }
}
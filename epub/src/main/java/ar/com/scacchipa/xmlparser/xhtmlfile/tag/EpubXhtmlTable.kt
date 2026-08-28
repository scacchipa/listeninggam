package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
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

    override fun getTextContained(): String {
        return (caption?.getTextContained() ?: "") +
                (colgroup?.getTextContained() ?: "") +
                (thead?.getTextContained() ?: "") +
                (tbody?.getTextContained() ?: "") +
                (tfoot?.getTextContained() ?: "")
    }
}
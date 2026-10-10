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
        return listOfNotNull(
            caption?.getTextContained()?.takeIf { it.isNotBlank() },
            colgroup?.getTextContained()?.takeIf { it.isNotBlank() },
            thead?.getTextContained()?.takeIf { it.isNotBlank() },
            tbody?.getTextContained()?.takeIf { it.isNotBlank() },
            tfoot?.getTextContained()?.takeIf { it.isNotBlank() }
        ).joinToString("\n")
    }
}
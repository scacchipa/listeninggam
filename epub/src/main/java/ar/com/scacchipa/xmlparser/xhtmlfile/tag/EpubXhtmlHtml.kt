package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBody
import org.xml.sax.Attributes

class EpubXhtmlHtml : EpubXhtmlTag {

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override val tagName: String = "html"

    var head: EpubXhtmlHead? = null
    var body: EpubXhtmlBody? = null

    override fun tagWrap(): String {
        return tagWrap(
            content = (head?.tagWrap() ?:"") + (body?.tagWrap() ?: "")
        )
    }

    override fun getTextContained(): String {
        return (head?.getTextContained() ?: "") + (body?.getTextContained() ?: "")
    }
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlNoscript : EpubXhtmlTag {
    override val tagName: String = "noscript"

    var text: MutableList<EpubXhtmlString> = mutableListOf()

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override fun tagWrap(): String {
        return tagWrap(content = text.joinToString("") { it.tagWrap() })
    }

    override fun getTextContained(): String = text.joinToString("") { it.getTextContained() }
}
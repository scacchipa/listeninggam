package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlBase : EpubXhtmlTag {
    override val tagName: String = "base"

    var text: MutableList<EpubXhtmlString> = mutableListOf()

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override fun tagWrap(): String {
        return tagWrap(content = text.joinToString("") { it.tagWrap() })
    }

    override fun getTextContained(): String {
        return text.joinToString("") {
            it.getTextContained()
        }
    }
}
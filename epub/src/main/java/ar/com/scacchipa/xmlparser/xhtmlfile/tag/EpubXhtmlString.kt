package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlString(
    val value: String,
) : EpubXhtmlTag() {
    override val tagName: String = "string"

    override fun tagWrap(): String {
        return value
    }

    override fun getTextContained(): String {
        return value
    }
}

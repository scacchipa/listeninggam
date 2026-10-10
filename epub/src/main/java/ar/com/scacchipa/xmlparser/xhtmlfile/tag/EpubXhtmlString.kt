package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.exts.collapseWhitespace
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlString : EpubXhtmlTag {
    val value: String

    constructor(value: String, normalize: Boolean = true) : super() {
        this.value = if (normalize) value.collapseWhitespace() else value.trim()
    }

    override val tagName: String = "string"

    override fun tagWrap(): String {
        return value
    }

    override fun getTextContained(): String {
        return value
    }
}

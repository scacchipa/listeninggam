package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.exts.normalizeWhitespace
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlString : EpubXhtmlTag {
    val value: String

    constructor(value: String) : super() {
        this.value = value.normalizeWhitespace()
    }

    override val tagName: String = "string"

    override fun tagWrap(): String {
        return value
    }

    override fun getTextContained(): String {
        return value
    }
}

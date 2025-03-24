package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlString(
    val value: String,
) : EpubXhtmlTag() {
    override val tagName: String = "string"

    override fun toString(): String {
        return tagWrap(content = value)
    }
}
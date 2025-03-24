package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlTitle : EpubXhtmlTag() {

    override val tagName: String = "title"

    var text: EpubXhtmlString? = null

    override fun toString(): String {
        return tagWrap(content = text.toString())
    }
}
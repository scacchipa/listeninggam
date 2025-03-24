package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBody

class EpubXhtmlHtml(
    val lang: String,
) : EpubXhtmlTag() {

    override val tagName: String = "html"

    var head: EpubXhtmlHead? = null
    var body: EpubXhtmlBody? = null

    override fun toString(): String {
        return tagWrap(
            attributes = listOf(lang),
            content = (head?.tagWrap() ?:"") + (body?.tagWrap() ?: "")
        )
    }
}
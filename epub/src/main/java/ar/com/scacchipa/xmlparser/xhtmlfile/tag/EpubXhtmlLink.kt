package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlLink(
    val rel: String,
    val href: String,
    val type: String,
) : EpubXhtmlTag() {

    override val tagName: String = "link"
}
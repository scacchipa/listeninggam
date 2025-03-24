package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlMeta(
    val httpEquiv: String,
    val content: String,
) : EpubXhtmlTag() {

    override val tagName: String = "meta"
}

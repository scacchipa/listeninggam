package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlCol(
    val span: String? = null,
    val width: String? = null,
    val height: String? = null,
    val style: String? = null
) : EpubXhtmlTag() {
    override val tagName: String = "col"
}
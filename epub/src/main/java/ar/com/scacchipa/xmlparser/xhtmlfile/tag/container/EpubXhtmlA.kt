package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag

class EpubXhtmlA(
    val href: String,
    val download: String,
    val hreflang: String,
    val referrerpolicy: String,
    val rel: String,
    val target: String,
    val type: String,
) : EpubXhtmlContainerTag() {

    override val tagName: String = "a"
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag

class EpubXhtmlImg(
    val alt: String,
    val crossorigin: String,
    val height: String,
    val ismap: String,
    val loading: String,
    val longdesc: String,
    val referrerpolicy: String,
    val sizes: String,
    val src: String,
    val srcset: String,
    val usemap: String,
    val width: String,
) : EpubXhtmlContainerTag() {

    override val tagName: String = "img"
}
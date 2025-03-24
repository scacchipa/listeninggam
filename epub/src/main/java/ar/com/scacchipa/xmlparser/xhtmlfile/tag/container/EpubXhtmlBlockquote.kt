package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlContainerTag

class EpubXhtmlBlockquote(
    val cite: String,
) : EpubXhtmlContainerTag() {

    override val tagName: String = "blockquote"
}
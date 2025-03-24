package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlUnknown(
    val tag: String
) : EpubXhtmlTag() {

    override val tagName: String = "unknown"
}
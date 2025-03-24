package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlColgroup : EpubXhtmlTag() {

    override val tagName: String = "colgroup"

    val cols: MutableList<EpubXhtmlCol> = mutableListOf()
}
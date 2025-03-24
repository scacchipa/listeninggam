package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlHead : EpubXhtmlTag() {

    override val tagName: String = "head"

    var title: EpubXhtmlTitle? = null
    var metas: MutableList<EpubXhtmlMeta> = mutableListOf()
    var links: MutableList<EpubXhtmlLink> = mutableListOf()
}
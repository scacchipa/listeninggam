package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlHead : IEpubXhtmlTag {
    var title: EpubXhtmlTitle? = null
    var metas: MutableList<EpubXhtmlMeta> = mutableListOf()
    var links: MutableList<EpubXhtmlLink> = mutableListOf()
}
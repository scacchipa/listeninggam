package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.container.EpubXhtmlBody

class EpubXhtmlHtml(
    val lang: String,
    var head: EpubXhtmlHead? = null,
    var body: EpubXhtmlBody? = null,
) : IEpubXhtmlTag
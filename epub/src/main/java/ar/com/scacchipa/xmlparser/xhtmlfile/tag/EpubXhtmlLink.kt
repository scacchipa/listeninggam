package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlLink(
    val rel: String,
    val href: String,
    val type: String,
) : IEpubXhtmlTag
package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlColgroup : IEpubXhtmlTag {
    val cols: MutableList<EpubXhtmlCol> = mutableListOf()
}
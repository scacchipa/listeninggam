package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlCell
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlTr : IEpubXhtmlTag {
    val cells = mutableListOf<IEpubXhtmlCell>()
}
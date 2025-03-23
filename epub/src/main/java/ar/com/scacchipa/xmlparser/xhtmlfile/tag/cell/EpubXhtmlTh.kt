package ar.com.scacchipa.xmlparser.xhtmlfile.tag.cell

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlCell
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlTh : IEpubXhtmlCell {
    override val content: MutableList<IEpubXhtmlTag> = mutableListOf()
}
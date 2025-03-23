package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlContainerTag
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlSpan : IEpubXhtmlContainerTag {
    override val content: MutableList<IEpubXhtmlTag> = mutableListOf()
}
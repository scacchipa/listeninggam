package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlContainerTag
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlSection: IEpubXhtmlContainerTag {
    override val content: MutableList<IEpubXhtmlTag> = mutableListOf()
}
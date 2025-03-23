package ar.com.scacchipa.xmlparser.xhtmlfile.tag.container

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlContainerTag
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlA(
    val href: String,
    val download: String,
    val hreflang: String,
    val referrerpolicy: String,
    val rel: String,
    val target: String,
    val type: String,
) : IEpubXhtmlContainerTag, IEpubXhtmlShape {
    override val content: MutableList<IEpubXhtmlTag> = mutableListOf()
}
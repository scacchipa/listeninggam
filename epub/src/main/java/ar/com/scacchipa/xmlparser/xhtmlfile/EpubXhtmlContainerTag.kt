package ar.com.scacchipa.xmlparser.xhtmlfile

import org.xml.sax.Attributes

abstract class EpubXhtmlContainerTag(attributes: Attributes) : EpubXhtmlTag(attributes) {

    val contents: MutableList<EpubXhtmlTag> = mutableListOf()

    override fun tagWrap() = tagWrap(contents.joinToString("") {
        it.tagWrap()
    })
}
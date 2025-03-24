package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlSvg(attributes: Attributes): EpubXhtmlTag(attributes) {

    val shapes: MutableList<EpubXhtmlShape> = mutableListOf()
    override val tagName: String = "svg"

    override fun toString(): String {
        return tagWrap(
            content = shapes.joinToString { it.tagWrap() }
        )
    }
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag

class EpubXhtmlSvg(
    val width: String,
    val height: String,
): EpubXhtmlTag() {

    val shapes: MutableList<EpubXhtmlShape> = mutableListOf()
    override val tagName: String = "svg"

    override fun toString(): String {
        return tagWrap(
            attributes =  listOf(width, height),
            content = shapes.joinToString { it.tagWrap() }
        )
    }
}
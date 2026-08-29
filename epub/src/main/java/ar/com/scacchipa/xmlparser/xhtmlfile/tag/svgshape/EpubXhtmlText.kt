package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan
import org.xml.sax.Attributes

class EpubXhtmlText(attributes: Attributes) : EpubXhtmlShape(attributes) {

    override val tagName: String = "text"

    val text = mutableListOf<EpubXhtmlTspan>()

    override fun getTextContained(): String = text.joinToString("") { it.getTextContained() }
}
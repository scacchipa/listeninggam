package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlCircle(
    val cx: String,
    val cy: String,
    val r: String,
) : EpubXhtmlShape() {

    override val tagName: String = "circle"
}
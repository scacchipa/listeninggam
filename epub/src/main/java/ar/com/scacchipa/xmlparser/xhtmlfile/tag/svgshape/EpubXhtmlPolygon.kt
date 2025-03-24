package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlPolygon(
    val points: String,
) : EpubXhtmlShape() {

    override val tagName: String = "polygon"
}
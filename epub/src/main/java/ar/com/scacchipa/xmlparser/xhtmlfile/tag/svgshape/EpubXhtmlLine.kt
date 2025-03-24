package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlLine(
    val x1: String,
    val y1: String,
    val x2: String,
    val y2: String,
) : EpubXhtmlShape() {

    override val tagName: String = "line"
}
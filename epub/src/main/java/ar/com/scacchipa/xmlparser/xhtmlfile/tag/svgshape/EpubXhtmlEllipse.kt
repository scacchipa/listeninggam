package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlEllipse(
    val cx: String,
    val cy: String,
    val rx: String,
    val ry: String,
) : EpubXhtmlShape() {
    override val tagName: String = "ellipse"
}
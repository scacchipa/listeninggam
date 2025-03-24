package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlRect(
    val x: String,
    val y: String,
    val width: String,
    val height: String,
    val rx: String,
) : EpubXhtmlShape() {

    override val tagName: String = "rect"
}
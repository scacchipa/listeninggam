package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlImage(
    val width: String,
    val height: String,
    val href: String,
    val x: String,
    val y: String,
) : EpubXhtmlShape() {

    override val tagName: String = "image"
}
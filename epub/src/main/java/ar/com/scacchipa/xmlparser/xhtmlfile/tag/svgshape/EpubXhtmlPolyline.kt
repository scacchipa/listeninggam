package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlPolyline(
    val points: String,
) : EpubXhtmlShape() {

    override val tagName: String = "polyline"
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape

class EpubXhtmlPath(
    val d: String,
) : EpubXhtmlShape() {

    override val tagName: String = "path"
}


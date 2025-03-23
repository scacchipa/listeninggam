package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlTspan

class EpubXhtmlText(
    val x: String = "",
    val y: String = "",
    val dx: String = "",
    val dy: String = "",
    val rotate: String = "",
    val textLength: String = "",
    val lengthAdjust: String = "",
) : IEpubXhtmlShape {
    val text = mutableListOf<EpubXhtmlTspan>()
}
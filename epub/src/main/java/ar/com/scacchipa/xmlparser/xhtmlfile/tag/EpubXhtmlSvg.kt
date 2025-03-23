package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlShape
import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlSvg(
    val width: String,
    val height: String,
): IEpubXhtmlTag {
    val shapes: MutableList<IEpubXhtmlShape> = mutableListOf()
}
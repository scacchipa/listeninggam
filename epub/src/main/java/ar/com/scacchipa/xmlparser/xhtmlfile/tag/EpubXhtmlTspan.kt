package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.IEpubXhtmlTag

class EpubXhtmlTspan(
    val x: String = "",
    val y: String = "",
    val dx: String = "",
    val dy: String = "",
    val rotate: String = "",
    val textLength: String = "",
    var text: String? = null
) : IEpubXhtmlTag
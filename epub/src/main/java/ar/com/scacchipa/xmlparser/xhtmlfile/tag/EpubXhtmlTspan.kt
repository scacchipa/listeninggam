package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlTspan : EpubXhtmlTag {

    constructor() : super()
    constructor(attributes: Attributes) : super(attributes)

    override val tagName: String = "tspan"

    var text: String? = null
}
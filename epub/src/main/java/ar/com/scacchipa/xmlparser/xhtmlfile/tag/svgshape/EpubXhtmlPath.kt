package ar.com.scacchipa.xmlparser.xhtmlfile.tag.svgshape

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlShape
import org.xml.sax.Attributes

class EpubXhtmlPath(attributes: Attributes) : EpubXhtmlShape(attributes) {

    override val tagName: String = "path"
}


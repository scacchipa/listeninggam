package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlUnknown(attributes: Attributes) : EpubXhtmlTag(attributes) {

    override val tagName: String = "unknown"
}
package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlHead(attributes: Attributes) : EpubXhtmlTag(attributes) {

    override val tagName: String = "head"

    var title: EpubXhtmlTitle? = null
    var metas: MutableList<EpubXhtmlMeta> = mutableListOf()
    var links: MutableList<EpubXhtmlLink> = mutableListOf()

    override fun tagWrap(): String {
        return tagWrap(
            (title?.tagWrap() ?: "") +
                    (metas.joinToString("") { it.tagWrap() }) +
                    (links.joinToString("") { it.tagWrap() })
        )
    }
}
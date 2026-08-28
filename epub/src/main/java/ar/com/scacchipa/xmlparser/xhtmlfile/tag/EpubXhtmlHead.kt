package ar.com.scacchipa.xmlparser.xhtmlfile.tag

import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import org.xml.sax.Attributes

class EpubXhtmlHead(attributes: Attributes) : EpubXhtmlTag(attributes) {

    override val tagName: String = "head"

    var title: EpubXhtmlTitle? = null
    var metas: MutableList<EpubXhtmlMeta> = mutableListOf()
    var links: MutableList<EpubXhtmlLink> = mutableListOf()
    var style: MutableList<EpubXhtmlStyle> = mutableListOf()
    var base: MutableList<EpubXhtmlBase> = mutableListOf()
    var script: MutableList<EpubXhtmlScript> = mutableListOf()
    var noscript: MutableList<EpubXhtmlNoscript> = mutableListOf()

    override fun tagWrap(): String {
        return tagWrap(
            (title?.tagWrap() ?: "") +
                    (metas.joinToString("") { it.tagWrap() }) +
                    (links.joinToString("") { it.tagWrap() }) +
                    (style.joinToString("") { it.tagWrap() }) +
                    (base.joinToString("") { it.tagWrap() }) +
                    (script.joinToString("") { it.tagWrap() }) +
                    (noscript.joinToString("") { it.tagWrap() })
        )
    }

    override fun getTextContained(): String {
        return title?.getTextContained() ?: ""
    }
}
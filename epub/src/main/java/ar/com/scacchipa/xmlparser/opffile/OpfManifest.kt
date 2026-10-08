package ar.com.scacchipa.xmlparser.opffile

class OpfManifest(
    val items: MutableList<OpfItem> = mutableListOf()
) : OpfTag {
    operator fun get(id: String): OpfItem? = items.find { it.id == id }

    fun getByHRef(href: String): OpfItem? = items.find { it.href == href }
}
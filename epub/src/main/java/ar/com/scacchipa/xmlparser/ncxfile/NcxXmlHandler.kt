package ar.com.scacchipa.xmlparser.ncxfile

import org.xml.sax.helpers.DefaultHandler
import java.util.Stack

class NcxXmlHandler : DefaultHandler() {
    private val tagStack = Stack<NcxTag>()

    private var ncx = Ncx()

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: org.xml.sax.Attributes?
    ) {
        println("🔹 Encontrado elemento: qName=$qName, localName=$localName, uri=$uri")
        if (attributes != null) {
            for (i in 0 until attributes.length) {
                println("   ➡ Atributo: ${attributes.getQName(i)} = ${attributes.getValue(i)}")
            }
        }

        when (qName) {
            "ncx" -> {
                val tag = Ncx().apply {
                    this.version = attributes?.getValue("version") ?: ""
                    this.lang = attributes?.getValue("xml:lang") ?: ""
                }
                tagStack.push(tag)
            }

            "head" -> {
                val tag = NcxHead()

                tagStack.push(tag)
            }

            "meta" -> {
                val tag = NcxMeta(
                    name = attributes?.getValue("name") ?: "",
                    content =attributes?.getValue("content") ?: "")

                tagStack.push(tag)
            }

            "docTitle" -> {
                val tag = NcxDocTitle()

                tagStack.push(tag)
            }

            "text" -> {
                val tag = NcxText()

                tagStack.push(tag)
            }

            "navMap" -> {
                val tag = NcxNavMap()

                tagStack.push(tag)
            }

            "navPoint" -> {
                val tag = NcxNavPoint(
                    id = attributes?.getValue("id") ?: "",
                    playOrder = attributes?.getValue("playOrder")?.toInt() ?: 0
                )

                tagStack.push(tag)
            }

            "navLabel" -> {
                val tag = NcxNavLabel()
                tagStack.push(tag)
            }

            "content" -> {
                val tag = NcxContent(
                    src = attributes?.getValue("src") ?: ""
                )
                tagStack.push(tag)
            }

            else -> {
                println("⚠️ Unknown element: $qName")
            }
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {

        val text = String(ch!!, start, length).trim()

        if (text.isNotEmpty()) {
            println("🔹 Caracteres: ch=$text, start=$start, length=$length")

            when (val tag = tagStack.peek()) {
                is NcxText -> tag.text = text
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {

        println("🔹 Cerrado elemento: qName=$qName, localName=$localName, uri=$uri")

        when (qName) {
            "ncx" -> {
                val tag = tagStack.pop() as Ncx

                this.ncx = tag
            }

            "head" -> {
                val tag = tagStack.pop() as NcxHead

                when (val parentTag = tagStack.peek()) {
                    is Ncx -> parentTag.head = tag as NcxHead
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "meta" -> {
                val tag = tagStack.pop() as NcxMeta

                when (val parentTag = tagStack.peek()) {
                    is NcxHead -> parentTag.metas.add(tag)
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "docTitle" -> {
                val tag = tagStack.pop() as NcxDocTitle

                when (val parentTag = tagStack.peek()) {
                    is Ncx -> parentTag.ncxDocTitle = tag
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "text" -> {
                val tag = tagStack.pop() as NcxText

                when (val parentTag = tagStack.peek()) {
                    is NcxDocTitle -> parentTag.text = tag
                    is NcxNavLabel -> parentTag.text = tag
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "navMap" -> {
                val tag = tagStack.pop() as NcxNavMap

                when (val parentTag = tagStack.peek()) {
                    is Ncx -> parentTag.navMap = tag
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "navPoint" -> {
                val tag = tagStack.pop() as NcxNavPoint

                when (val parentTag = tagStack.peek()) {
                    is NcxNavMap -> parentTag.navPoints.add(tag)
                    is NcxNavPoint -> parentTag.navPoints.add(tag)
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "navLabel" -> {
                val tag = tagStack.pop() as NcxNavLabel

                when (val parentTag = tagStack.peek()) {
                    is NcxNavPoint -> parentTag.navLabels.add(tag)
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            "content" -> {
                val tag = tagStack.pop() as NcxContent

                when (val parentTag = tagStack.peek()) {
                    is NcxNavPoint -> parentTag.ncxContent = tag
                    else -> println("⚠️ Unknown head parent: $parentTag, tag = $tag")
                }
            }

            else -> println("⚠️ Unknown element closed: $qName")
        }
    }

    fun getNcx(): Ncx = ncx
}
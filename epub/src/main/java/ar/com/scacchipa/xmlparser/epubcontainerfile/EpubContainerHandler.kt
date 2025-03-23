package ar.com.scacchipa.xmlparser.epubcontainerfile

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.util.Stack

class EpubContainerHandler : DefaultHandler() {
        private val tagStack = Stack<EpubContainerXmlTag>()

        private var epubContainer: EpubContainer = EpubContainer()

        override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
            println("🔹 Encontrado elemento: qName=$qName, localName=$localName, uri=$uri")
            if (attributes != null) {
                for (i in 0 until attributes.length) {
                    println("   ➡ Atributo: ${attributes.getQName(i)} = ${attributes.getValue(i)}")
                }
            }

            when (qName) {
                "container" -> tagStack.push(EpubContainer(
                        version = attributes.getValue("version")
                    ))
                "rootfiles" -> tagStack.push(EpubRootFiles())
                "rootfile" -> tagStack.push(EpubRootfile(
                    fullPath = attributes.getValue("full-path"),
                    mediaType = attributes.getValue("media-type")
                ))
                else -> println("⚠️ Unknown element opened: $qName")
            }
        }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        val text = String(ch!!, start, length).trim()

        if (text.isNotEmpty()) {
            println("🔹 Caracteres: ch=$text, start=$start, length=$length")

            when (val tag = tagStack.peek()) {
//                is NcxText -> tag.text = text
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        println("🔹 Cerrado elemento: qName=$qName, localName=$localName, uri=$uri")

        when (qName) {
            "container" -> {
                val lastTag = tagStack.pop() as EpubContainer
                epubContainer = lastTag
            }
            "rootfiles" -> {
                val lastTag = tagStack.pop() as EpubRootFiles
                (tagStack.peek() as EpubContainer).rootFiles = lastTag
            }
            "rootfile" -> {
                val lastTag = tagStack.pop() as EpubRootfile
                (tagStack.peek() as EpubRootFiles).rootFiles.add(lastTag)
            }

            else -> println("⚠️ Unknown element closed: $qName")
        }

    }

    fun getContainer() : EpubContainer {
        return epubContainer
    }
}
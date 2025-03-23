package ar.com.scacchipa.xmlparser.opffile

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.util.Stack


/**
 * Open package format
 *
 * source: https://idpf.org/epub/20/spec/OPF_2.0_latest.htm#Section2.1
 */

class OpfXmlHandler : DefaultHandler() {

    private val tagStack = Stack<OpfTag>()

    private var opf = OpfPackage("", null, null)

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        println("🔹 Encontrado elemento: qName=$qName, localName=$localName, uri=$uri")
        if (attributes != null) {
            for (i in 0 until attributes.length) {
                println("   ➡ Atributo: ${attributes.getQName(i)} = ${attributes.getValue(i)}")
            }
        }

        when (qName) {
            "package" -> tagStack.push(
                OpfPackage(
                    uniqueIdentifier = attributes?.getValue("unique-identifier") ?: "",
                    version = attributes?.getValue("version"),
                    xmlns = attributes?.getValue("xmlns"),
                )
            )

            "metadata" -> tagStack.push(OpfMetadata())
            "dc:title" -> tagStack.push(OpfTitle())
            "dc:creator" -> tagStack.push(OpfCreator())
            "dc:subject" -> tagStack.push(OpfSubject())
            "dc:description" -> tagStack.push(OpfDescription())
            "dc:publisher" -> tagStack.push(OpfPublisher())
            "dc:contributor" -> tagStack.push(OpfContributor())
            "dc:date" -> tagStack.push(OpfDate())
            "dc:type" -> tagStack.push(OpfType())
            "dc:format" -> tagStack.push(OpfFormat())
            "dc:identifier" -> tagStack.push(OpfIdentifier())
            "dc:source" -> tagStack.push(OpfSource())
            "dc:language" -> tagStack.push(OpfLanguage())
            "dc:relation" -> tagStack.push(OpfRelation())
            "dc:coverage" -> tagStack.push(OpfCoverage())
            "dc:rights" -> tagStack.push(OpfRights())
            "meta" -> tagStack.push(
                OpfMeta(
                    property = attributes?.getValue("property") ?: "",
                    refines = attributes?.getValue("refines") ?: "",
                    scheme = attributes?.getValue("scheme"),
                )
            )

            "manifest" -> tagStack.push(OpfManifest())
            "item" -> tagStack.push(
                OpfItem(
                    id = attributes?.getValue("id") ?: "",
                    href = attributes?.getValue("href") ?: "",
                    mediaType = attributes?.getValue("media-type") ?: "",
                    requiredNamespace = attributes?.getValue("required-namespace") ?: "",
                    fallback = attributes?.getValue("fallback") ?: "",
                )
            )

            "spine" -> tagStack.push(
                OpfSpine(
                    toc = attributes?.getValue("toc") ?: "",
                )
            )

            "itemref" -> tagStack.push(
                OpfItemRef(
                    id = attributes?.getValue("idref") ?: "",
                    href = attributes?.getValue("idref") ?: "",
                )
            )

            "guide" -> tagStack.push(OpfGuide())

            "reference" -> tagStack.push(
                OpfReference(
                    type = attributes?.getValue("type") ?: "",
                    title = attributes?.getValue("title") ?: "",
                    href = attributes?.getValue("href") ?: "",
                ))

            else -> println("⚠️ Unknown tag in startElement: $qName")
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        val text = String(ch!!, start, length).trim()

        if (text.isNotEmpty()) {
            println("🔹 Caracteres: ch=$text, start=$start, length=$length")

            when (val tag = tagStack.peek()) {
                is OpfTitle -> tag.text = text
                is OpfCreator -> tag.text = text
                is OpfRights -> tag.text = text
                is OpfIdentifier -> tag.text = text
                is OpfMeta -> tag.content = text
                is OpfSubject -> tag.text = text
                is OpfDescription -> tag.text = text
                is OpfPublisher -> tag.text = text
                is OpfContributor -> tag.text = text
                is OpfDate -> tag.text = text
                is OpfType -> tag.text = text
                is OpfFormat -> tag.text = text
                is OpfSource -> tag.text = text
                is OpfLanguage -> tag.text = text
                is OpfRelation -> tag.text = text
                is OpfCoverage -> tag.text = text
                else -> println("⚠️ Unknown character: $text")
            }
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        println("🔹 Cerrado elemento: qName=$qName, localName=$localName, uri=$uri")

        when (qName) {
            "package" -> opf = tagStack.pop() as OpfPackage

            "metadata" -> {
                val tag = tagStack.pop() as OpfMetadata
                (tagStack.peek() as OpfPackage).metadata = tag
            }

            "dc:title" -> {
                val tag = tagStack.pop() as OpfTitle
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:creator" -> {
                val tag = tagStack.pop() as OpfCreator
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:subject" -> {
                val tag = tagStack.pop() as OpfSubject
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:description" -> {
                val tag = tagStack.pop() as OpfDescription
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:publisher" -> {
                val tag = tagStack.pop() as OpfPublisher
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:contributor" -> {
                val tag = tagStack.pop() as OpfContributor
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:date" -> {
                val tag = tagStack.pop() as OpfDate
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:type" -> {
                val tag = tagStack.pop() as OpfType
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:format" -> {
                val tag = tagStack.pop() as OpfFormat
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:identifier" -> {
                val tag = tagStack.pop() as OpfIdentifier
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:source" -> {
                val tag = tagStack.pop() as OpfSource
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:language" -> {
                val tag = tagStack.pop() as OpfLanguage
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:relation" -> {
                val tag = tagStack.pop() as OpfRelation
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:coverage" -> {
                val tag = tagStack.pop() as OpfCoverage
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "dc:rights" -> {
                val tag = tagStack.pop() as OpfRights
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "meta" -> {
                val tag = tagStack.pop() as OpfMeta
                (tagStack.peek() as OpfMetadata).content.add(tag)
            }
            "manifest" -> {
                val tag = tagStack.pop() as OpfManifest
                (tagStack.peek() as OpfPackage).manifest = tag
            }
            "item" -> {
                val tag = tagStack.pop() as OpfItem
                (tagStack.peek() as OpfManifest).items.add(tag)
            }
            "spine" -> {
                val tag = tagStack.pop() as OpfSpine
                (tagStack.peek() as OpfPackage).spine = tag
            }
            "itemref" -> {
                val tag = tagStack.pop() as OpfItemRef
                (tagStack.peek() as OpfSpine).data.add(tag)
            }
            "guide" -> {
                val tag = tagStack.pop() as OpfGuide
                (tagStack.peek() as OpfPackage).guide = tag
            }
            "reference" -> {
                val tag = tagStack.pop() as OpfReference
                (tagStack.peek() as OpfGuide).references.add(tag)
            }

            else -> println("⚠️ Unknown tag in endElement: $qName")
        }
    }

    fun getOpf(): OpfPackage = opf
}
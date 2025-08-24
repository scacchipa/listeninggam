package ar.com.scacchipa.xmlparser.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.xmlparser.data.EPubContainerXml
import ar.com.scacchipa.xmlparser.data.OpfContainerXml
import ar.com.scacchipa.xmlparser.epubcontainerfile.EpubContainerHandler
import ar.com.scacchipa.xmlparser.ncxfile.NcxXmlHandler
import ar.com.scacchipa.xmlparser.opffile.OpfXmlHandler
import ar.com.scacchipa.xmlparser.util.ZipUtils
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXHtmlHandler
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import org.xml.sax.InputSource
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.SAXParserFactory
import kotlin.io.path.Path

class OpenEpubUseCase @Inject constructor(
    private val zipUtils: ZipUtils
) {

    operator  fun invoke(assetName: String) {
        parse(zipUtils.unzip(assetName))
    }

    operator fun invoke(@RawRes id: Int) {
        parse(zipUtils.unzip(id))
    }

    private fun parse(ePubMap: Map<String, String>): MutableList<EpubXhtmlHtml> {

        val documentBuilder = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()

        val ePubContainerXml = EPubContainerXml(
            document = documentBuilder.parse(ePubMap["/META-INF/container.xml"]?.byteInputStream())
                .also { it.normalizeDocument() }
        )

        val opfContainerXml = OpfContainerXml(
            path = Path(ePubContainerXml.opfFullPath),
            document = documentBuilder.parse(ePubMap["/${ePubContainerXml.opfFullPath}"]?.byteInputStream())
                .also { it.normalizeDocument() }
        )

        val factory = SAXParserFactory.newInstance()

        // Parse epub container xml file
        val containerSaxParser = factory.newSAXParser()
        val containerHandler = EpubContainerHandler()

        ePubMap["/META-INF/container.xml"]?.byteInputStream().use { inputStream ->
            containerSaxParser.parse(InputSource(inputStream), containerHandler)
        }

        // Parse OPF xml file
        val opfSaxParser = factory.newSAXParser()
        val opfHandler = OpfXmlHandler()

        ePubMap["/${ePubContainerXml.opfFullPath}"]?.byteInputStream().use { inputStream ->
            opfSaxParser.parse(InputSource(inputStream), opfHandler)
        }

        val opf = opfHandler.getOpf()

        // Parse NCX xml file
        val ncxSaxParser = factory.newSAXParser()
        val ncxHandler = NcxXmlHandler()

        ePubMap["/${opfContainerXml.tocNcxFullPath}"]?.byteInputStream().use { inputStream ->
            ncxSaxParser.parse(InputSource(inputStream), ncxHandler)
        }

        val ncx = ncxHandler.getNcx()


        // Convert Items from Manifest

        val htmlFiles = mutableListOf<EpubXhtmlHtml>()
        opf.manifest?.items?.forEach { item ->
            val mediaType = item.mediaType

            when (mediaType) {
                "application/xhtml+xml" -> {

                    val xhtmlSaxParser = factory.newSAXParser()
                    val xhtmlHandler = EpubXHtmlHandler()

                    ePubMap["/OEBPS/${item.href}"]?.byteInputStream().use { inputStream ->
                        println("Parsing: ${item.href}")
                        xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
                        val xhtml = xhtmlHandler.getXhtml()

                        println(xhtml.tagWrap())

                        htmlFiles.add(xhtml)
                    }

                }
                else -> {
                    println("MediaType: $mediaType converter didn't found")
                }
            }
        }

        println(ncx)
        println("tocContent")

        return htmlFiles
    }
}


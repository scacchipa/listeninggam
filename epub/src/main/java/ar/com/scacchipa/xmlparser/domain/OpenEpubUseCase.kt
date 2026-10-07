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
    operator  fun invoke(assetName: String): List<EpubXhtmlHtml> {
        return parse(zipUtils.unzip(assetName))
    }

    operator fun invoke(@RawRes id: Int): List<EpubXhtmlHtml> {
        return parse(zipUtils.unzip(id))
    }

    private fun parse(ePubMap: Map<String, String>): List<EpubXhtmlHtml> {

        val documentBuilder = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()

        val containerXmlStr = ePubMap["/META-INF/container.xml"] ?: ePubMap["META-INF/container.xml"]
            ?: throw IllegalStateException("container.xml not found in EPUB")

        val ePubContainerXml = EPubContainerXml(
            document = documentBuilder.parse(containerXmlStr.byteInputStream())
                .also { it.normalizeDocument() }
        )

        val opfPath = ePubContainerXml.opfFullPath
        val opfXmlStr = ePubMap["/$opfPath"] ?: ePubMap[opfPath]
            ?: throw IllegalStateException("OPF file $opfPath not found in EPUB")

        val opfContainerXml = OpfContainerXml(
            path = Path(opfPath),
            document = documentBuilder.parse(opfXmlStr.byteInputStream())
                .also { it.normalizeDocument() }
        )

        val factory = SAXParserFactory.newInstance()

        // Parse epub container xml file
        val containerSaxParser = factory.newSAXParser()
        val containerHandler = EpubContainerHandler()

        containerXmlStr.byteInputStream().use { inputStream ->
            containerSaxParser.parse(InputSource(inputStream), containerHandler)
        }

        // Parse OPF xml file
        val opfSaxParser = factory.newSAXParser()
        val opfHandler = OpfXmlHandler()

        opfXmlStr.byteInputStream().use { inputStream ->
            opfSaxParser.parse(InputSource(inputStream), opfHandler)
        }

        val opf = opfHandler.getOpf()

        // Parse NCX xml file
        val ncxSaxParser = factory.newSAXParser()
        val ncxHandler = NcxXmlHandler()

        val ncxPath = opfContainerXml.tocNcxFullPath
        val ncxXmlStr = ePubMap["/$ncxPath"] ?: ePubMap[ncxPath]
        ncxXmlStr?.byteInputStream()?.use { inputStream ->
            ncxSaxParser.parse(InputSource(inputStream), ncxHandler)
        }

        val ncx = ncxHandler.getNcx()

        val opfParentDir = if (opfPath.contains("/")) opfPath.substringBeforeLast("/") else ""

        val htmlFiles: List<EpubXhtmlHtml> = opf.spineRefs
            .mapNotNull { spineRef -> opf.manifest?.items?.find { it.id == spineRef.id } }
            .mapNotNull { item ->
                val xhtmlSaxParser = factory.newSAXParser()
                val xhtmlHandler = EpubXHtmlHandler()

                val href = item.href
                val fullPath = if (opfParentDir.isNotEmpty()) "$opfParentDir/$href" else href
                val xhtmlXmlStr =
                    ePubMap["/$fullPath"] ?: ePubMap[fullPath] ?: ePubMap["/OEBPS/$href"]
                    ?: ePubMap["OEBPS/$href"] ?: ePubMap[href]
                    ?: return@mapNotNull null

                println("Parsing: $fullPath")

                val xhtml:  EpubXhtmlHtml
                xhtmlXmlStr.byteInputStream().use { inputStream ->
                    xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
                    xhtml = xhtmlHandler.getXhtml()
                    println("xhtml " + xhtml.getTextContained())
                }
                return@mapNotNull xhtml
            }

        println(ncx)
        println("tocContent")

        return htmlFiles.toList()
    }
}

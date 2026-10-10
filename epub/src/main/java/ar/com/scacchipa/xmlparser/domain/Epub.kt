package ar.com.scacchipa.xmlparser.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.xmlparser.data.EPubContainerXml
import ar.com.scacchipa.xmlparser.epubcontainerfile.EpubContainerHandler
import ar.com.scacchipa.xmlparser.ncxfile.Ncx
import ar.com.scacchipa.xmlparser.ncxfile.NcxXmlHandler
import ar.com.scacchipa.xmlparser.opffile.OpfPackage
import ar.com.scacchipa.xmlparser.opffile.OpfXmlHandler
import ar.com.scacchipa.xmlparser.util.ZipUtils
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXHtmlHandler
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlHtml
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.SAXParserFactory

class Epub {

    /** Contains the metadata, manifest, and spine (reading order) parsed from the main OPF file of
     *  the EPUB.
     * */
    lateinit var opfPackage: OpfPackage

    /** Contains the navigation structure and table of contents (Navigation Control XML) of the
     * book.
     * */
    lateinit var ncx: Ncx

    /** List of XHTML documents that make up the reading content of the EPUB book, parsed and
     * structured.
     * */
    lateinit var htmlFiles: List<EpubXhtmlHtml>

    constructor(assetName: String, zipUtils: ZipUtils) {
        parse(zipUtils.unzip(assetName))
    }

    constructor(@RawRes id: Int, zipUtils: ZipUtils) {
        parse(zipUtils.unzip(id))
    }

    private fun parse(ePubMap: Map<String, String>): List<EpubXhtmlHtml> {

        val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val factory = SAXParserFactory.newInstance()
        val ePubContainerXml = getContainer(ePubMap, documentBuilder)

        opfPackage = getOpf(ePubMap, factory, ePubContainerXml)

        ncx = getNcx(ePubMap, factory, opfPackage)

        htmlFiles = getHtmlFiles(ePubMap, factory, opfPackage)

        println(ncx)
        println("tocContent")

        return htmlFiles.toList()
    }

    private fun getContainer(
        ePubMap: Map<String, String>, documentBuilder: DocumentBuilder
    ): EPubContainerXml {
        val containerXmlStr = ePubMap["/META-INF/container.xml"]
            ?: ePubMap["META-INF/container.xml"]
            ?: throw IllegalStateException("container.xml not found in EPUB")

        return EPubContainerXml(
            document = documentBuilder.parse(containerXmlStr.byteInputStream())
                .also { it.normalizeDocument() }
        )
    }

    private fun getOpf(
        ePubMap: Map<String, String>,
        factory: SAXParserFactory,
        ePubContainerXml: EPubContainerXml,
    ): OpfPackage {
        val opfPath = ePubContainerXml.opfFullPath
        val opfXmlStr = ePubMap["/$opfPath"]
            ?: ePubMap[opfPath]
            ?: throw IllegalStateException("OPF file $opfPath not found in EPUB")


        val containerSaxParser = factory.newSAXParser()
        val containerHandler = EpubContainerHandler()

        val containerXmlStr = ePubMap["/META-INF/container.xml"]
            ?: ePubMap["META-INF/container.xml"]
            ?: throw IllegalStateException("container.xml not found in EPUB")

        containerXmlStr.byteInputStream().use { inputStream ->
            containerSaxParser.parse(InputSource(inputStream), containerHandler)
        }

        // Parse OPF xml file
        val opfSaxParser = factory.newSAXParser()
        val opfHandler = OpfXmlHandler()

        opfXmlStr.byteInputStream().use { inputStream ->
            opfSaxParser.parse(InputSource(inputStream), opfHandler)
        }

        return opfHandler.getOpf()
    }

    private fun getNcx(
        ePubMap: Map<String, String>,
        factory: SAXParserFactory,
        opfPackage: OpfPackage,
    ): Ncx {
        val ncxSaxParser = factory.newSAXParser()
        val ncxHandler = NcxXmlHandler()

        val ncxPath = opfPackage.tocNcxFullPath
        val ncxXmlStr = ePubMap["/$ncxPath"] ?: ePubMap[ncxPath]
        ncxXmlStr?.byteInputStream()?.use { inputStream ->
            ncxSaxParser.parse(InputSource(inputStream), ncxHandler)
        }

        return ncxHandler.getNcx()
    }

    private fun getHtmlFiles(
        ePubMap: Map<String, String>,
        factory: SAXParserFactory,
        opfPackage: OpfPackage,
    ): List<EpubXhtmlHtml> {
        return opfPackage.spineRefs
            .mapNotNull { spineRef -> opfPackage.manifest?.items?.find { it.id == spineRef.id } }
            .mapNotNull { item ->
                val xhtmlSaxParser = factory.newSAXParser()
                val xhtmlHandler = EpubXHtmlHandler()

                val href = item.href
                val opfParentDir = opfPackage.opfParentDir
                val fullPath = if (opfParentDir.isNotEmpty()) "$opfParentDir/$href" else href
                val xhtmlXmlStr =
                    ePubMap["/$fullPath"] ?: ePubMap[fullPath] ?: ePubMap["/OEBPS/$href"]
                    ?: ePubMap["OEBPS/$href"] ?: ePubMap[href]
                    ?: return@mapNotNull null

                println("Parsing: $fullPath")

                val xhtml: EpubXhtmlHtml
                xhtmlXmlStr.byteInputStream().use { inputStream ->
                    xhtmlSaxParser.parse(InputSource(inputStream), xhtmlHandler)
                    xhtml = xhtmlHandler.getXhtml()
                    println("xhtml " + xhtml.getTextContained())
                }
                return@mapNotNull xhtml
            }
    }
}
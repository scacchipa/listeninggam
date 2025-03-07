package ar.com.scacchipa.xmlparser.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.xmlparser.data.EPubContainerXml
import ar.com.scacchipa.xmlparser.data.OpfContainerXml
import ar.com.scacchipa.xmlparser.ncxfile.NcxXmlHandler
import ar.com.scacchipa.xmlparser.opffile.OpfXmlHandle
import ar.com.scacchipa.xmlparser.util.ZipUtils
import org.xml.sax.InputSource
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.SAXParserFactory
import kotlin.io.path.Path

class OpenEpubUseCase @Inject constructor(
    private val zipUtils: ZipUtils
) {
    operator fun invoke(@RawRes id: Int) {
        val ePubMap = zipUtils.unzip(id)

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

        val opfSaxParser = factory.newSAXParser()
        val opfHandler = OpfXmlHandle()

        ePubMap["/${ePubContainerXml.opfFullPath}"]?.byteInputStream().use { inputStream ->
            opfSaxParser.parse(InputSource(inputStream), opfHandler)
        }

        val opf = opfHandler.getOpf()

        val ncxSaxParser = factory.newSAXParser()
        val ncxHandler = NcxXmlHandler()

        ePubMap["/${opfContainerXml.tocNcxFullPath}"]?.byteInputStream().use { inputStream ->
            ncxSaxParser.parse(InputSource(inputStream), ncxHandler)
        }

        val ncx = ncxHandler.getNcx()
        println(ncx)
        println("tocContent")
    }
}
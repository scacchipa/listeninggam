package ar.com.scacchipa.xmlparser.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.xmlparser.data.EPubContainerXml
import ar.com.scacchipa.xmlparser.ncxfile.NcxXmlHandler
import ar.com.scacchipa.xmlparser.data.OpfContainerXml
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
        val saxParser = factory.newSAXParser()
        val handle = NcxXmlHandler()

        ePubMap["/${opfContainerXml.tocNcxFullPath}"]?.byteInputStream().use { inputStream ->
            saxParser.parse(InputSource(inputStream), handle)
        }

        val ncx = handle.getNcx()
        println(ncx)
        println("tocContent")
    }
}
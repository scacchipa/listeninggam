package ar.com.scacchipa.epub.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.epub.data.EPubContainerXml
import ar.com.scacchipa.epub.data.OpfContainerXml
import ar.com.scacchipa.epub.data.TocContainerXml
import ar.com.scacchipa.epub.util.ZipUtils
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory
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

        val tocContent = TocContainerXml(
            path = Path(opfContainerXml.tocNcxFullPath),
            document = documentBuilder.parse(ePubMap["/${opfContainerXml.tocNcxFullPath}"]?.byteInputStream())
                .also { it.normalizeDocument() }
        )
        println(tocContent)
    }
}

class EpubDocument(
    val path: String,
    val head: Head,
    val docTitle: String,
    val navMap: List<String>
)

data class Head(
    val meta: Map<String, String>,
)
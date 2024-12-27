package ar.com.scacchipa.epub.domain

import androidx.annotation.RawRes
import ar.com.scacchipa.epub.data.EPubContainerFile
import ar.com.scacchipa.epub.data.EPubContainerXml
import ar.com.scacchipa.epub.util.ZipUtils
import javax.inject.Inject
import javax.xml.parsers.DocumentBuilderFactory

class OpenEpubUseCase @Inject constructor(
    private val zipUtils: ZipUtils
) {

    operator fun invoke(@RawRes id: Int) {
        val ePubMap = zipUtils.unzip(id)

        println(ePubMap["/META-INF/container.xml"])

        val documentBuilder = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()


        val document = documentBuilder.parse(ePubMap["/META-INF/container.xml"]?.byteInputStream())

        val opfPullPath = EPubContainerXml(document).getOpfFullPath()

        val contentOpf = documentBuilder.parse(ePubMap["/$opfPullPath"]?.byteInputStream())
        contentOpf.normalizeDocument()

        val ePubContainerFile = EPubContainerFile(contentOpf)
        println(ePubContainerFile.getMetadata())

    }
}
package ar.com.scacchipa.xmlparser.data

import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.inject.Inject

class EPubContainerXml @Inject constructor(
    document: Document
) {
    val opfFullPath =  getOpfFullPath(document)

    fun getOpfFullPath(document: Document): String {
        val container = document.getElementsByTagName("container").item(0) as Element
        val rootFiles = container.getElementsByTagName("rootfiles").item(0) as Element
        val rootFile = rootFiles.getElementsByTagName("rootfile").item(0) as Element
        val rootFileFullPath = rootFile.getAttribute("full-path")

        println("full-path: $rootFileFullPath")
        return rootFileFullPath
    }
}

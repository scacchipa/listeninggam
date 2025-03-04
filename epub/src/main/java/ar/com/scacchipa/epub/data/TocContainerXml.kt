package ar.com.scacchipa.epub.data

import ar.com.scacchipa.epub.ext.allElements
import ar.com.scacchipa.epub.ext.allText
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.nio.file.Path

class TocContainerXml(
    val path: Path,
    val document: Document
) {
    val version: String = document.documentElement.getAttribute("version")
    val lang: String = document.documentElement.getAttribute("xml:lang")

    val metas = (document.documentElement.getElementsByTagName("head").item(0) as Element)
        .getElementsByTagName("meta").allElements()

    val uid = metas.firstOrNull {
        it.getAttribute("name") == "dtb:uid"
    }?.getAttribute("content")
    val depth = metas.firstOrNull {
        it.getAttribute("name") == "dtb:depth"
    }?.getAttribute("content")?. toIntOrNull()

    val totalpageCount = metas.firstOrNull {
        it.getAttribute("name") == "dtb:totalPageCount"
    }?.getAttribute("content")?. toIntOrNull()

    val maxPageNumber =  metas.firstOrNull {
        it.getAttribute("name") == "dtb:maxPageNumber"
    }?.getAttribute("content")?. toIntOrNull()

     val docTitle = document.documentElement.getElementsByTagName("docTitle")
         .allElements().firstOrNull()?.getElementsByTagName("text")?.allText()?.map { it.nodeValue }

    val docAuthor = document.documentElement.getElementsByTagName("docAuthor")
        .allElements().firstOrNull()?.getElementsByTagName("text")?.allText()?.map { it.nodeValue }


    val navMap = document.documentElement.getElementsByTagName("navMap").item(0) as Element

    val navPoints = navMap.getElementsByTagName("navPoint").allElements()
   val x = "docTitle" + " ewqewq"

}


class NavMap(
    val id: String,
    val playOrder: Int,
    val label: String,
    val content: String,
    val navinfo: List<NavInfo>,
    val navPoints: List<NavPoint>
)

class NavPoint(
    val title: String,
    val label: List<NavLabel>,
    val navPoints: List<NavPoint>?,
    val navLabel: String,
    val contenteRef: String
)

class NavLabel(
    val text: String?
)

class NavInfo(
    val text: String
)
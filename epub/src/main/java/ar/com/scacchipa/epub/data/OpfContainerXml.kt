package ar.com.scacchipa.epub.data

import ar.com.scacchipa.epub.data.dtos.EPubGuideDto
import ar.com.scacchipa.epub.data.dtos.EPubManifestDto
import ar.com.scacchipa.epub.data.dtos.EPubMetadataDto
import ar.com.scacchipa.epub.data.dtos.EpubSpineDto
import ar.com.scacchipa.epub.data.dtos.ManifestItemDto
import ar.com.scacchipa.epub.ext.allElements
import ar.com.scacchipa.epub.ext.allText
import ar.com.scacchipa.epub.ext.firstText
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.nio.file.Path
import javax.inject.Inject

class OpfContainerXml @Inject constructor(
    private val path: Path,
    document: Document
) {
    val uniqueIdentifier = document.documentElement.getAttribute("unique-identifier")
    val metadata = buildMetadata(document)

    val manifest = buildManifest(document)
    val spine = buildSpine(document)
    val guide = buildGuide(document)

    val tocNcxFullPath =  path.parent.toString() + "/" + manifest.items[spine.toc]?.href.orEmpty()


    private fun buildMetadata(document: Document): EPubMetadataDto {

        val pack = document.getElementsByTagName("package").item(0) as Element
        val metadata = pack.getElementsByTagName("metadata").item(0) as Element

        return EPubMetadataDto(
            identifier = metadata.getElementsByTagName("dc:identifier").firstText().wholeText,
            rights = metadata.getElementsByTagName("dc:rights").firstText().wholeText,
            authors = metadata.getElementsByTagName("dc:creator").allText().map { it.wholeText },
            title = metadata.getElementsByTagName("dc:title").firstText().wholeText,
            language = metadata.getElementsByTagName("dc:language").firstText().wholeText,
            subjects = metadata.getElementsByTagName("dc:subject").allText().map { it.wholeText },
            date = metadata.getElementsByTagName("dc:date").firstText().wholeText,
            source = metadata.getElementsByTagName("dc:source").firstText().wholeText,
        )
    }

    private fun buildManifest(document: Document): EPubManifestDto {
        val pack = document.getElementsByTagName("package").item(0) as Element
        val manifest = pack.getElementsByTagName("manifest").item(0) as Element

        return EPubManifestDto(
            items = manifest.getElementsByTagName("item").allElements().associate {
                it.getAttribute("id") to ManifestItemDto(
                    id = it.getAttribute("id"),
                    href = it.getAttribute("href"),
                    mediaType = it.getAttribute("media-type"),
                )
            }
        )
    }

    private fun buildSpine(document: Document): EpubSpineDto {
        val pack = document.getElementsByTagName("package").item(0) as Element
        val spine = pack.getElementsByTagName("spine").item(0) as Element

        val toc = spine.getAttribute("toc")

        val itemRefs = spine.getElementsByTagName("itemref").allElements().map { it.getAttribute("idref") }

        return EpubSpineDto(toc, itemRefs)
    }

    private fun buildGuide(document: Document): List<EPubGuideDto> {
        val pack = document.getElementsByTagName("package").item(0) as Element
        val guide = pack.getElementsByTagName("guide").item(0) as? Element

        return guide?.getElementsByTagName("reference")?.allElements()?.map {
            EPubGuideDto(
                type = it.getAttribute("type"),
                title = it.getAttribute("title"),
                href = it.getAttribute("href")
            )
        } ?: emptyList()
    }
}


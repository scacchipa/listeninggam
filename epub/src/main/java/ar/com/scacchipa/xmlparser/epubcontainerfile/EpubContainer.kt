package ar.com.scacchipa.xmlparser.epubcontainerfile

class EpubContainer(
    val version: String = "",

    var rootFiles: EpubRootFiles? = null
) : EpubContainerXmlTag
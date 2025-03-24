package ar.com.scacchipa.xmlparser.xhtmlfile

abstract class EpubXhtmlTag {

    abstract val tagName: String

    fun tagWrap(
        attributes: List<String> = listOf(), content: String? = null
    ) =
        "<$tagName" + (if (attributes.none { it.isNotBlank() }) "" else " " + attributes.joinToString(separator = " ")) + ">" + (content
            ?: "") + "</$tagName>"
}
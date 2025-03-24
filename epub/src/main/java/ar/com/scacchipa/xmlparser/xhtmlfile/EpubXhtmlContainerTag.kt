package ar.com.scacchipa.xmlparser.xhtmlfile

abstract class EpubXhtmlContainerTag : EpubXhtmlTag() {

    val contents: MutableList<EpubXhtmlTag> = mutableListOf()

    fun tagWrap(
        attributes: List<String> = listOf(),
    ) = "<$tagName ${
        attributes.joinToString(
            separator = " ",
            prefix = " "
        )
    }>${contents.joinToString()}</$tagName>"
}
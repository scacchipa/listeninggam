package ar.com.scacchipa.xmlparser.xhtmlfile

import org.xml.sax.Attributes

abstract class EpubXhtmlTag {

    abstract val tagName: String

    private val attributes: MutableMap<String, String>

    constructor() {
        this.attributes = mutableMapOf()
    }

    constructor(attributes: Attributes) {
        this.attributes = mutableMapOf()
        (0..<attributes.length).forEach { idx ->
            setAttr(attributes.getQName(idx), attributes.getValue(idx))
        }
    }

    constructor(attributes: Map<String, String>) {
        this.attributes = attributes as MutableMap
    }

    fun setAttr(key: String, value: String) {
        attributes[key] = value
    }

    fun getAttr(key: String): String {
        return attributes[key] ?: ""
    }

    open fun tagWrap() = "<$tagName" + (if (attributes.isEmpty()) ""
    else " " + attributes.map { entry -> "${entry.key}=\"${entry.value}\"" }
        .joinToString(separator = " ")) + "/>"

    open fun tagWrap(
        content: String
    ) = "<$tagName" + (if (attributes.isEmpty()) ""
    else " " + attributes.map { entry -> "${entry.key}=\"${entry.value}\"" }
        .joinToString(separator = " ")) + ">" + content  + "</$tagName>"

    abstract fun getTextContained(): String
}
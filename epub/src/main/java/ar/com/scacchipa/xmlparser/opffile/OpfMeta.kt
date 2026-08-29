package ar.com.scacchipa.xmlparser.opffile

class OpfMeta(
    val property: String,
    val refines: String,
    val scheme: String?,

    var content: String? = null
) : OpfTag
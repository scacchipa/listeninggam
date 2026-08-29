package ar.com.scacchipa.xmlparser.opffile

class OpfItem(
    val id: String,
    val href: String,
    val mediaType: String,
    val requiredNamespace: String,
    val fallback: String,
) : OpfTag
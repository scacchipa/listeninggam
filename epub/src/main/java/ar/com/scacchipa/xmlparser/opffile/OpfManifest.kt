package ar.com.scacchipa.xmlparser.opffile

class OpfManifest(
    val items: MutableList<OpfItem> = mutableListOf()
) : OpfTag
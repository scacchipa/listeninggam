package ar.com.scacchipa.xmlparser.opffile

class OpfSpine(
    val toc: String = "ncx",
    val data: MutableList<OpfItemRef> = mutableListOf()
) : OpfTag
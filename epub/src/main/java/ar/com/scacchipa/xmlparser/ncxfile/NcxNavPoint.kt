package ar.com.scacchipa.xmlparser.ncxfile

class NcxNavPoint(
    val id: String?,
    val playOrder: Int?,

    var ncxContent: NcxContent? = null,
    val navPoints: MutableList<NcxNavPoint> = mutableListOf(),
    val navLabels: MutableList<NcxNavLabel> = mutableListOf()
) : NcxTag


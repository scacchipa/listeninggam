package ar.com.scacchipa.xmlparser.ncxfile

class NcxNavMap(
    val id: String? = null,
    val navPoints: MutableList<NcxNavPoint> = mutableListOf(),
    val navLabel: MutableList<NcxNavLabel> = mutableListOf(),
    val navInfo: MutableList<NcxNavInfo> = mutableListOf(),
) : NcxTag
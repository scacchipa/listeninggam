package ar.com.scacchipa.xmlparser.ncxfile

class Ncx (
    var version : String? = null,
    var lang : String? = null,

    var head : NcxHead? = null,

    var ncxDocTitle: NcxDocTitle? = null,
    var navMap: NcxNavMap? = null,
)  : NcxTag
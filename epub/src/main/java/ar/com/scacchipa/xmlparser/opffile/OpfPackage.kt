package ar.com.scacchipa.xmlparser.opffile

class OpfPackage(
    var uniqueIdentifier : String,
    var version: String?,
    var xmlns : String?,

    var metadata : OpfMetadata? = null,
    var manifest : OpfManifest? = null,
    var spine : OpfSpine? = null,
    var guide : OpfGuide? = null,
) : OpfTag
package ar.com.scacchipa.xmlparser.opffile

class OpfPackage(
    var uniqueIdentifier : String,
    var version: String?,
    var xmlns : String?,

    var metadata : OpfMetadata? = null,
    var manifest : OpfManifest? = null,
    var spine : OpfSpine? = null,
    var guide : OpfGuide? = null,

) : OpfTag {

    val spineRefs: List<OpfItemRef>
        get(): List<OpfItemRef> = spine?.data ?: emptyList()

    val tocNcxFullPath: String
        get(): String {
            val tocPath = spine?.toc ?: return ""
            return manifest?.getByHRef(tocPath)?.href.orEmpty()
        }

    val opfParentDir =
        if (tocNcxFullPath.contains("/"))
            tocNcxFullPath.substringBeforeLast("/")
        else ""
}

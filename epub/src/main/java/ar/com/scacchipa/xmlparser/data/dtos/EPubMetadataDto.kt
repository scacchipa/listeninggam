package ar.com.scacchipa.xmlparser.data.dtos

data class EPubMetadataDto(
    var identifier: String = "",
    val rights: String = "",
    val authors: List<String> = listOf(),
    val title: String = "",
    val language: String = "",
    val subjects: List<String> = listOf(),
    val date: String = "",
    val source: String = "",
)
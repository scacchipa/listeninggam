package ar.com.scacchipa.epub.data.dtos

data class EPubMetadataDto(
    val rights: String = "",
    val authors: List<String> = listOf(),
    val title: String = "",
    val language: String = "",
    val subjects: List<String> = listOf(),
    val date: String = "",
    val source: String = "",
)
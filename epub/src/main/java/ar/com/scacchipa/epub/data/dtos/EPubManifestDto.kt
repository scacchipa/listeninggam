package ar.com.scacchipa.epub.data.dtos

data class EPubManifestDto(
    val items: Map<String, ManifestItemDto> = mapOf()
)
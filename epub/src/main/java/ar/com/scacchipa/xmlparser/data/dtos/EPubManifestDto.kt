package ar.com.scacchipa.xmlparser.data.dtos

data class EPubManifestDto(
    val items: Map<String, ManifestItemDto> = mapOf()
)
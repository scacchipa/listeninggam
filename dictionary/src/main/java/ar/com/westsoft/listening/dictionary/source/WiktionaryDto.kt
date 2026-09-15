package ar.com.westsoft.listening.dictionary.source

import com.google.gson.annotations.SerializedName

data class WiktionaryItem(
    @SerializedName("partOfSpeech") val partOfSpeech: String,
    @SerializedName("language") val language: String,
    @SerializedName("definitions") val definitions: List<WiktionaryDefinition>
)

data class WiktionaryDefinition(
    @SerializedName("definition") val definition: String,
    @SerializedName("examples") val examples: List<String>?
)

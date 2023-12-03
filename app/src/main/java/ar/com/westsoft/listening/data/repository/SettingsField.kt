package ar.com.westsoft.listening.data.repository

data class SettingsField<T: Any>(
    val value: T,
    val wasSaved: Boolean
)
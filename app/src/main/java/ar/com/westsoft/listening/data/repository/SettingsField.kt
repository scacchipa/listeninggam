package ar.com.westsoft.listening.data.repository

data class SettingsField<T>(
    val value: T,
    val wasSaved: Boolean
)
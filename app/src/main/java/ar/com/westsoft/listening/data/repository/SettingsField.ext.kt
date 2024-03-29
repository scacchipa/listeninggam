package ar.com.westsoft.listening.data.repository

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

fun SettingsField<out Any>.toAnnotatedString() = buildAnnotatedString {
    if (!this@toAnnotatedString.wasSaved) {
        pushStyle(SpanStyle(color = Color.Red))
    }
    append(this@toAnnotatedString.value.toString())
}

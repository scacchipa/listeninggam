package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.util

fun Char.normalize(): Char =
    when(this) {
        'á' -> 'a'
        'Á' -> 'A'
        'é' -> 'e'
        'É' -> 'E'
        'í' -> 'i'
        'Í' -> 'I'
        'ó' -> 'o'
        'Ó' -> 'O'
        'ú' -> 'u'
        'Ú' -> 'U'
        else -> this
    }
package ar.com.westsoft.listening.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeechProvider @Inject constructor() {
    // https://www1.udel.edu/LLL/language/deutsch/handouts/summer_2015/Schatzinsel_E.pdf
    fun getSpeech() = "I remember him as if it were yesterday, as he came plodding to the inn door, his " +
            "sea- chest following behind him in a hand-barrow — a tall, strong, heavy, nut-brown " +
            "man, his tarry pigtail falling over the shoulder of his soiled blue coat, his hands " +
            "ragged and scarred, with black, broken nails, and the sabre cut across one cheek, a " +
            "dirty, livid white. I remember him looking round the cover and whistling to himself " +
            "as he did so, and then breaking out in that old sea-song that he sang so often " +
            "afterwards:"
}
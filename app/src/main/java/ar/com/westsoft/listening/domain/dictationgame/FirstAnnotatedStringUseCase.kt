package ar.com.westsoft.listening.domain.dictationgame

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.engine.DictationGame
import javax.inject.Inject

class FirstAnnotatedStringUseCase @Inject constructor(
    private val dictationGame: DictationGame
){
    operator fun invoke(): AnnotatedString {
        return dictationGame.getFirstAnnotatedString()
    }
}

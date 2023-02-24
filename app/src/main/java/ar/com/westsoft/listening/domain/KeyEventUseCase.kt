package ar.com.westsoft.listening.domain

import androidx.compose.ui.input.key.KeyEvent
import ar.com.westsoft.listening.service.DictationGame
import javax.inject.Inject

class KeyEventUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(keyEvent: KeyEvent) {
        dictationGame.onKeyEvent(keyEvent)
    }
}
package ar.com.westsoft.listening.domain.dictationgame

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.engine.DictationGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class AnnotationStringFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame,
) {
    operator fun invoke(scope: CoroutineScope): StateFlow<AnnotatedString> {
        return dictationGame.getAnnotatedStringFlow().stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = dictationGame.getFirstAnnotatedString()
        )
    }
}
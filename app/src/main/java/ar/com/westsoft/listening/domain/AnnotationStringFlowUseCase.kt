package ar.com.westsoft.listening.domain

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.service.DictationGame
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnnotationStringFlowUseCase @Inject constructor(
    private val dictationGame: DictationGame
) {
    operator fun invoke(): Flow<AnnotatedString> {
        return dictationGame.getAnnotatedStringFlow()
    }
}
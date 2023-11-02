package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.engine.GetDictationGameStateFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetFormatTextInRow
import ar.com.westsoft.listening.domain.dictationgame.engine.MoveToParagraphUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.SpeakOutUseCase
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameConsoleViewModel @Inject constructor(
    private val getDictationGameStateFlowUseCase: GetDictationGameStateFlowUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    private val moveToParagraphUseCase: MoveToParagraphUseCase,
    private val getFormatTextInRow: GetFormatTextInRow
) : ViewModel() {

    fun getDictationGameStateFlow(): Flow<DictGameState> =
        getDictationGameStateFlowUseCase()

    fun onLetterClicked(offset: Int) {
        viewModelScope.launch {
            speakOutUseCase(offset = offset)
        }
    }

    fun onParagraphClick(paragraphIdx: Int) {
        viewModelScope.launch {
            moveToParagraphUseCase(paragraphIdx)
        }
    }

    fun getFormatText(charArray: CharArray):AnnotatedString {
        return getFormatTextInRow(charArray)
    }
}

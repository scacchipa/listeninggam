package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.domain.dictationgame.engine.GetDictationGameStateFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetFormatTextInRow
import ar.com.westsoft.listening.domain.dictationgame.engine.GetStartPositionToShowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.MoveToParagraphUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.SpeakOutUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.GetDictSettingFlowUseCase
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GameConsoleViewModel @Inject constructor(
    private val getDictationGameStateFlowUseCase: GetDictationGameStateFlowUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    private val moveToParagraphUseCase: MoveToParagraphUseCase,
    private val getFormatTextInRow: GetFormatTextInRow,
    private val getDictSettingFlowUseCase: GetDictSettingFlowUseCase,
    private val getStartPositionToShowUseCase: GetStartPositionToShowUseCase
) : ViewModel() {

    var dictGameState = getDictationGameStateFlow()
        .onEach {
            println("ViewModel: paragraph: ${it.cursorPosition.paragraphIdx}," +
                    " cursorRow: ${it.cursorPosition.row}, cursorCol: ${it.cursorPosition.column}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DictGameState(0, CursorPosition(), 0, AnnotatedString(""), DictationGameRecord())
        )

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

    fun getFormatText(charArray: CharArray): AnnotatedString {
        return getFormatTextInRow(charArray)
    }

    fun getSetting(): DictGameSettings {
        return runBlocking {
            getDictSettingFlowUseCase().first()
        }
    }

    fun getStartParagraphToShow(
        cursorParagraph: Int,
        cursorRow: Int,
        rewindRow: Int
    ): Pair<Int, Int> {
        return getStartPositionToShowUseCase(
            cursorParagraph = cursorParagraph,
            cursorRow = cursorRow,
            rewindRow = 5
        )
    }
}

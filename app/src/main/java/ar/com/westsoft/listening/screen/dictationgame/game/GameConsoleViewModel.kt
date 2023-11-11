package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.domain.dictationgame.engine.GetDictationGameStateFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetFormatTextInRowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetStartPositionToShowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.MoveToParagraphUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.SpeakOutUseCase
import ar.com.westsoft.listening.domain.dictationgame.settings.GetDictSettingFlowUseCase
import ar.com.westsoft.listening.screen.dictationgame.game.DictGameState
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.engine.DictationProgressSizeUseCase
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
    private val getFormatTextInRowUseCase: GetFormatTextInRowUseCase,
    private val getDictSettingFlowUseCase: GetDictSettingFlowUseCase,
    private val getStartPositionToShowUseCase: GetStartPositionToShowUseCase,
    private val getDictationProgressUseCase: DictationProgressSizeUseCase
) : ViewModel() {

    var dictGameState = getDictationGameStateFlow()
        .onEach {
            println("ViewModel: paragraph: ${it.cursorPos.paragraphIdx}," +
                    " cursorRow: ${it.cursorPos.row}, cursorCol: ${it.cursorPos.column}")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DictGameState(0, ComplexCursorPos(), 0, AnnotatedString(""))
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

    fun getProgressListSize(): Int? {
        return getDictationProgressUseCase()
    }

    fun getFormatText(charArray: CharArray): AnnotatedString {
        return getFormatTextInRowUseCase(charArray)
    }

    fun getFormatText(paragraphIdx: Int): AnnotatedString {
        return getFormatTextInRowUseCase(paragraphIdx)
    }

    fun getSetting(): DictGameSettings {
        return runBlocking {
            getDictSettingFlowUseCase().first()
        }
    }

    fun getStartParagraphToShow(
        numberRowAbove: Int
    ): Pair<Int, Int>? {
        return getStartPositionToShowUseCase(
            cursorPos = dictGameState.value.cursorPos,
            numberRowAbove = numberRowAbove
        )
    }
}

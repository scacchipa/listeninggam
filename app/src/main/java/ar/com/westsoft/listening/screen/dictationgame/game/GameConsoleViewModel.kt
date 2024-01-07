package ar.com.westsoft.listening.screen.dictationgame.game

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.com.westsoft.listening.data.game.SimpleCursorPos
import ar.com.westsoft.listening.domain.dictationgame.engine.ConsoleViewState
import ar.com.westsoft.listening.domain.dictationgame.engine.GetDictationGameStateFlowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetFormatTextInRowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetStartPositionToShowUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.MoveToParagraphUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.SpeakOutUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.DictationProgressSizeUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetColumnPerPageUseCase
import ar.com.westsoft.listening.domain.dictationgame.engine.GetComplexCursorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameConsoleViewModel @Inject constructor(
    getDictationGameStateFlowUseCase: GetDictationGameStateFlowUseCase,
    private val speakOutUseCase: SpeakOutUseCase,
    private val moveToParagraphUseCase: MoveToParagraphUseCase,
    private val getFormatTextInRowUseCase: GetFormatTextInRowUseCase,
    private val getStartPositionToShowUseCase: GetStartPositionToShowUseCase,
    private val getDictationProgressUseCase: DictationProgressSizeUseCase,
    private val getColumnPerPageUseCase: GetColumnPerPageUseCase,
    private val getComplexCursorUseCase: GetComplexCursorUseCase
) : ViewModel() {

    var cursorPosStateFlow = getDictationGameStateFlowUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ConsoleViewState(
                simpleCursorPos = SimpleCursorPos(),
                utterance = null
            )
        )

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

    fun getFormatText(paragraphIdx: Int): AnnotatedString {
        return getFormatTextInRowUseCase(paragraphIdx)
    }

    fun getComplexCursor(simpleCursor: SimpleCursorPos): ComplexCursorPos? =
        getComplexCursorUseCase(simpleCursor)

    fun getStartParagraphToShow(
        simpleCursor: SimpleCursorPos,
        numberRowAbove: Int
    ): ComplexCursorPos? {
        return getStartPositionToShowUseCase(
            complexCursorPos = getComplexCursor(simpleCursor) ?: ComplexCursorPos(0, 0, 0),
            numberRowAbove = numberRowAbove
        )
    }

    fun getHorizontalShift(cursorCol: Int, parentWidthPx: Int, widthPx: Int): Int {
        val columnPerPage = getColumnPerPageUseCase()
        val leftMargin = 10
        val rightMargin = 15

        return if (parentWidthPx < widthPx) {
            when {
                cursorCol < leftMargin -> 0

                cursorCol > columnPerPage - rightMargin ->
                    widthPx - parentWidthPx

                else -> (widthPx - parentWidthPx) /
                        (columnPerPage - leftMargin - rightMargin) * (cursorCol - leftMargin)
            }
        } else {
            0
        }
    }
}

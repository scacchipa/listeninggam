package ar.com.westsoft.listening.screen.keyboard

import android.graphics.PointF
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainKeyBoard(
    modifier: Modifier = Modifier,
    sideKey: Dp
) {
    val viewModel = hiltViewModel<KeyBoardViewModel>()
    Box(
        modifier = modifier
    ) {

        QuertyKeyBoard(
            sideKey = sideKey,
            viewModel = viewModel
        )
        FunctionKeyBoard(
            sideKey = sideKey,
            offset = DpOffset(sideKey * 10.5f, 0.dp),
            viewModel = viewModel
        )
        ArrowsKeyBoard(
            sideKey = sideKey,
            offset = DpOffset(sideKey * 8.5f, sideKey * 3f),
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuertyKeyBoard(
    sideKey: Dp,
    offset: PointF = PointF(0f,0f),
    viewModel: KeyBoardViewModel
) {
    Column(
        modifier = Modifier.offset(
            x = offset.x.dp,
            y = offset.y.dp
        )
    ) {
        Row {
            KeyButton(key = Key.One, text = "1", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Two, text = "2", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Three, text = "3", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Four, text = "4", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Five, text = "5", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Six, text = "6", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Seven, text = "7", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Eight, text = "8", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Nine, text = "9", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Zero, text = "0", height = sideKey, viewModel = viewModel)
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)

        }
        Row {
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
            KeyButton(key = Key.Q, text = "Q", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.W, text = "W", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.E, text = "E", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.R, text = "R", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.T, text = "T", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Y, text = "Y", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.U, text = "U", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.I, text = "I", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.O, text = "O", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.P, text = "P", height = sideKey, viewModel = viewModel)
        }
        Row {
            InvisibleKeyButton(height = sideKey, width = sideKey)
            KeyButton(key = Key.A, text = "A", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.S, text = "S", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.D, text = "D", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.F, text = "F", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.G, text = "G", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.H, text = "H", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.J, text = "J", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.K, text = "K", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.L, text = "L", height = sideKey, viewModel = viewModel)
            InvisibleKeyButton(height = sideKey, width = sideKey / 2)
        }
        Row {
            InvisibleKeyButton(height = sideKey, width = sideKey * 3 / 2)
            KeyButton(key = Key.Z, text = "Z", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.X, text = "X", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.C, text = "C", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.V, text = "V", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.B, text = "B", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.N, text = "N", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.M, text = "M", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.Spacebar, text = "_", height = sideKey, width = sideKey,
                viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FunctionKeyBoard(
    sideKey: Dp,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    viewModel: KeyBoardViewModel
) {
    Column(
        modifier = Modifier.offset(
            x = offset.x,
            y = offset.y
        )
    ) {
        KeyButton(key = Key.Spacebar, text = "Rd", height = sideKey, width = sideKey * 2, viewModel = viewModel)
        KeyButton(key = Key.Apostrophe, text = "Lt", height = sideKey, width = sideKey * 2, viewModel = viewModel)
        KeyButton(key = Key.Backslash, text = "Wd", height = sideKey, width = sideKey * 2, viewModel = viewModel)
        KeyButton(key = Key.Equals, text = "Ph", height = sideKey, width = sideKey * 2, viewModel = viewModel)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ArrowsKeyBoard(
    sideKey: Dp,
    offset: DpOffset = DpOffset(0.dp,0.dp),
    viewModel: KeyBoardViewModel
) {
    Column(
        modifier = Modifier.offset(
            x = offset.x,
            y = offset.y
        )
    ) {
        Row {
            NullKeyButton(height = sideKey)
            KeyButton(key = Key.DirectionUp, text = "↑", height = sideKey, viewModel = viewModel)
            NullKeyButton(height = sideKey)
        }
        Row {
            KeyButton(key = Key.DirectionLeft, text = "←", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.DirectionDown, text = "↓", height = sideKey, viewModel = viewModel)
            KeyButton(key = Key.DirectionRight, text = "→", height = sideKey, viewModel = viewModel)
        }
    }
}

package ar.com.scacchipa.keyboard.screen

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.input.key.Key

val LocalKeyboardPressedKeys = staticCompositionLocalOf<Set<Key>> {
    emptySet()
}

val LocalKeyboardOnKeyAction = staticCompositionLocalOf<(Key) -> Unit> {
    {}
}

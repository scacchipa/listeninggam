package ar.com.scacchipa.keyboard.screen

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.input.key.Key

val LocalKeyboardPressedKeys = staticCompositionLocalOf<Set<Key>> {
    emptySet()
}

val LocalKeyboardOnKeyAction = staticCompositionLocalOf<(Key) -> Unit> {
    {}
}

val Key.isFunctional: Boolean
    get() = when (this) {
        Key.Spacebar,
        Key.DirectionRight,
        Key.DirectionLeft,
        Key.DirectionUp,
        Key.DirectionDown,
        Key.Enter,
        Key.Apostrophe,
        Key.Backslash,
        Key.Equals -> true
        else -> false
    }

package ar.com.westsoft.listening.data

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.Key
import javax.inject.Inject

class Keyboard @Inject constructor() {
    @OptIn(ExperimentalComposeUiApi::class)
    fun toChar(key: Key) = when(key) {
        Key.Zero -> '0'
        Key.One -> '1'
        Key.Two -> '2'
        Key.Three -> '3'
        Key.Four -> '4'
        Key.Five -> '5'
        Key.Six -> '6'
        Key.Seven -> '7'
        Key.Eight -> '8'
        Key.Nine -> '9'
        Key.A -> 'A'
        Key.B -> 'B'
        Key.C -> 'C'
        Key.D -> 'D'
        Key.E -> 'E'
        Key.F -> 'F'
        Key.G -> 'G'
        Key.H -> 'H'
        Key.I -> 'I'
        Key.J -> 'J'
        Key.K -> 'K'
        Key.L -> 'L'
        Key.M -> 'M'
        Key.N -> 'N'
        Key.O -> 'O'
        Key.P -> 'P'
        Key.Q -> 'Q'
        Key.R -> 'R'
        Key.S -> 'S'
        Key.T -> 'T'
        Key.U -> 'U'
        Key.V -> 'V'
        Key.W -> 'W'
        Key.X -> 'X'
        Key.Y -> 'Y'
        Key.Z -> 'Z'
        else -> null
    }
}
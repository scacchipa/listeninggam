package ar.com.westsoft.listening.screen.dictationgame.navigation

sealed class GameCreationGameStatus {
    object Uninitialized : GameCreationGameStatus()
    class Completed(val gui: Long) : GameCreationGameStatus()
    object Error : GameCreationGameStatus()
    object IsDownloading: GameCreationGameStatus()
}
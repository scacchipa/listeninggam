package ar.com.westsoft.listening.screen.dictationgame

sealed class GameCreationGameStatus {
    object Uninitialized : GameCreationGameStatus()
    class Completed(val gui: Long) : GameCreationGameStatus()
    object Error : GameCreationGameStatus()
    object IsDownloading: GameCreationGameStatus()
}
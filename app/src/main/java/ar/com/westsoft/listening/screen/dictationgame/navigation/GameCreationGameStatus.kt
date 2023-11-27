package ar.com.westsoft.listening.screen.dictationgame.navigation

sealed class GameCreationGameStatus {
    object Uninitialized : GameCreationGameStatus()
    object Completed : GameCreationGameStatus()
    object Error : GameCreationGameStatus()
    object IsDownloading: GameCreationGameStatus()
}

package ar.com.westsoft.listening.screen.dictationgame.navigation

import ar.com.westsoft.listening.domain.dictationgame.repository.SourceFile

sealed class GameCreationGameStatus {
    object Uninitialized : GameCreationGameStatus()
    class Completed(val gui: Long) : GameCreationGameStatus()
    object Error : GameCreationGameStatus()
    class IsLoading(val source: SourceFile): GameCreationGameStatus()
}

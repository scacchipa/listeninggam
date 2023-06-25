package ar.com.westsoft.listening.data.engine

data class Game(
    val gameHeader: GameHeader = GameHeader(),
    val dictationProgressList: List<DictationProgress> = listOf()
)


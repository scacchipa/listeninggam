package ar.com.westsoft.listening.data.engine

data class GameEntity(
    val gui: Long = 0,
    val title: String = "",
    val txtAddress: String = "",
    val dictationProgressEntity: DictationProgressEntity = DictationProgressEntity()
)
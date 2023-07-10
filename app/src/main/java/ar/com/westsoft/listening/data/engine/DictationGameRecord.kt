package ar.com.westsoft.listening.data.engine

data class DictationGameRecord(
    val gameHeader: DictationGameHeader = DictationGameHeader(),
    val dictationProgressList: List<DictationProgress> = listOf()
)


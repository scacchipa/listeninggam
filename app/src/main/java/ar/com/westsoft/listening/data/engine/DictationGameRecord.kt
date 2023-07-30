package ar.com.westsoft.listening.data.engine

data class DictationGameRecord(
    val gameHeader: DictationGameHeader = DictationGameHeader(),
    val dictationProgressList: List<DictationProgress> = listOf()
) {
    fun getProgressRate(): Double {
        val progressList = dictationProgressList.filter {
            it.originalTxt.isNotEmpty()
        }
        val totalLinesCount = progressList.size
        val completedLinesCount = progressList.count { progress ->
            progress.originalTxt == progress.progressTxt.concatToString()
        }
        return completedLinesCount.toDouble() / totalLinesCount
    }
}


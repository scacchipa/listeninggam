package ar.com.westsoft.listening.data.game

data class DictationGameRecord(
    val gameHeader: DictationGameHeader = DictationGameHeader(),
    val dictationProgressList: List<DictationProgress> = listOf()
) {
    fun getGlobalProgressRate(): Double {
        val progressList = dictationProgressList.filter {
            it.originalTxt.isNotEmpty()
        }
        return progressList.count { it.isCompleted() }.toDouble() / progressList.size
    }
}

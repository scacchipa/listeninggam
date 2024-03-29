package ar.com.westsoft.listening.data.datasource

data class DictGameSettingsDSO(
    val readWordAfterCursor: Int,
    val readWordBeforeCursor: Int,
    val speechRate: Float,
    val speedLevel: SpeedLevelPreference,
    val columnPerPage: Int
)

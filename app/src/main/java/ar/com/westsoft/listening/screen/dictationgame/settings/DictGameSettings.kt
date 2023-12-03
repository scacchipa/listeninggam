package ar.com.westsoft.listening.screen.dictationgame.settings

import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference

data class DictGameSettings(
    val readWordAfterCursor: Int,
    val readWordBeforeCursor: Int,
    val speechRatePercentage: Float,
    val speedLevel: SpeedLevelPreference,
    val columnPerPage: Int
)

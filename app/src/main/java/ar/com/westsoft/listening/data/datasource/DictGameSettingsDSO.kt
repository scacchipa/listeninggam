package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.SpeedLevelPreference

data class DictGameSettingsDSO(
    val readWordAfterCursor: Int,
    val readWordBeforeCursor: Int,
    val speechRate: Float,
    val speedLevel: SpeedLevelPreference,
    val columnPerPage: Int
)

package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.domain.dictationgame.settings.SpeedLevelPreference

data class DictGameSettingsDSO(
    val readWordAfterCursor: Int,
    val readWordBeforeCursor: Int,
    val speechRate: Float,
    val speedLevel: SpeedLevelPreference
)

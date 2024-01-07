package ar.com.westsoft.listening.data.datasource

enum class SpeedLevelPreference {
    LOW_SPEED_LEVEL,
    MEDIUM_SPEED_LEVEL,
    NORMAL_SPEED_LEVEL,
    HIGH_SPEED_LEVEL;

    companion object {
        fun fromKey(key: String): SpeedLevelPreference =
            SpeedLevelPreference.values().find {
                it.name == key
            } ?: NORMAL_SPEED_LEVEL
    }
}

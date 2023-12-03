package ar.com.westsoft.listening.data.datasource

import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings

fun DictGameSettingsDSO.toSetting() =
    DictGameSettings(
        readWordAfterCursor = readWordAfterCursor,
        readWordBeforeCursor = readWordBeforeCursor,
        speechRatePercentage = speechRate,
        speedLevel = speedLevel,
        columnPerPage = columnPerPage
    )

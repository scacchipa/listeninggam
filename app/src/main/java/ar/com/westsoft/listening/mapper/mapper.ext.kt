package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.datasource.GameHeaderEntity
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.util.Field
import ar.com.westsoft.listening.util.concatenate

fun DictationProgress.toEntity() = DictationProgressEntity(
    progressId = progressId,
    gameHeaderId = Field.unknown,
    originalTxt = originalTxt,
    progressTxt = progressTxt.concatenate()
)

fun DictationProgressEntity.toEngine() = DictationProgress(
    progressId = progressId,
    originalTxt = originalTxt,
    progressTxt = progressTxt.toCharArray()
)

fun DictationGameHeader.toEntity() =
    GameHeaderEntity(
        gui = gui,
        title = title,
        txtAddress = txtAddress,
        progressRate = progressRate
    )

fun GameHeaderEntity.toEngine() =
    DictationGameHeader(
        gui = gui,
        title = title,
        txtAddress = txtAddress,
        progressRate = progressRate
    )

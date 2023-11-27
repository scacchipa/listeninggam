package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
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

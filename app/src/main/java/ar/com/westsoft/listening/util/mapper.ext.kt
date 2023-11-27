package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.util

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.datasource.GameHeaderEntity
import ar.com.westsoft.listening.data.datasource.SavedDictationGameEntity
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.util.Field
import ar.com.westsoft.listening.util.concatenate

fun DictationProgress.toEntity(): DictationProgressEntity =
    DictationProgressEntity(progressId, Field.unknown, originalTxt, progressTxt.concatenate())

fun DictationProgressEntity.toEngine(): DictationProgress =
    DictationProgress(progressId, originalTxt, progressTxt.toCharArray())

fun DictationGameHeader.toEntity(): GameHeaderEntity =
    GameHeaderEntity(gui, title, txtAddress, progressRate)

fun GameHeaderEntity.toEngine(): DictationGameHeader =
    DictationGameHeader(gui, title, txtAddress, progressRate)

fun List<DictationProgress>.toEntity(): List<DictationProgressEntity> =
    this.map { it.toEntity() }

fun List<DictationProgressEntity>.toEngine(): List<DictationProgress> =
    this.map { it.toEngine() }

fun DictationGameRecord.toEntity(): SavedDictationGameEntity =
    SavedDictationGameEntity(
        gameHeaderEntity = gameHeader.toEntity(),
        dictationProgressEntityLists = dictationProgressList.toEntity()
            .onEach { it.gameHeaderId = gameHeader.gui }
    )

fun SavedDictationGameEntity.toEngine(): DictationGameRecord =
    DictationGameRecord(
        gameHeader = gameHeaderEntity.toEngine(),
        dictationProgressList = dictationProgressEntityLists.toEngine()
    )

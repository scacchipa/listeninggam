package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.mapper.toEngine
import javax.inject.Inject

class DictationProgressListMapper @Inject constructor(): Mapper<List<DictationProgress>, List<DictationProgressEntity>> {
    override fun toDataSource(origin: List<DictationProgress>): List<DictationProgressEntity> =
        origin.map { it.toEntity() }

    override fun toEngine(origin: List<DictationProgressEntity>): List<DictationProgress> =
        origin.map { it.toEngine() }
}

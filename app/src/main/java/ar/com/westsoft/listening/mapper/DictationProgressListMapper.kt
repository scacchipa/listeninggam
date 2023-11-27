package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.game.DictationProgress
import javax.inject.Inject

class DictationProgressListMapper @Inject constructor(
    private val dictationProgressMapper: DictationProgressMapper
): Mapper<List<DictationProgress>, List<DictationProgressEntity>> {
    override fun toDataSource(origin: List<DictationProgress>): List<DictationProgressEntity> =
        origin.map { dictationProgressMapper.toDataSource(it) }

    override fun toEngine(origin: List<DictationProgressEntity>): List<DictationProgress> =
        origin.map { dictationProgressMapper.toEngine(it) }
}

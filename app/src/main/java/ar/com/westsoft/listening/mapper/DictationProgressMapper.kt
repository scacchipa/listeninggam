package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.engine.DictationProgress
import ar.com.westsoft.listening.util.Field
import javax.inject.Inject

class DictationProgressMapper @Inject constructor()
    : Mapper<DictationProgress, DictationProgressEntity> {
    override fun toDataSource(origin: DictationProgress): DictationProgressEntity {
        return DictationProgressEntity(
            progressId = origin.progressId,
            gameHeaderId = Field.unknown,
            originalTxt = origin.originalTxt,
            progressTxt = origin.progressTxt.joinToString("")
        )
    }

    override fun toEngine(origin: DictationProgressEntity): DictationProgress {
        return DictationProgress(
            progressId = origin.progressId,
            originalTxt = origin.originalTxt,
            progressTxt = origin.progressTxt.toCharArray()
        )
    }
}

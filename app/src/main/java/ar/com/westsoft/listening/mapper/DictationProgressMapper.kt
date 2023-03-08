package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.DictationProgressDto
import ar.com.westsoft.listening.data.engine.DictationProgressEntity
import javax.inject.Inject

class DictationProgressMapper @Inject constructor()
    : Mapper<DictationProgressEntity, DictationProgressDto> {
    override fun toDataSource(origin: DictationProgressEntity): DictationProgressDto {
        return DictationProgressDto(
            originalTxt = origin.originalTxt,
            progressTxt = origin.progressTxt.joinToString("")
        )
    }

    override fun toEngine(origin: DictationProgressDto): DictationProgressEntity {
        return DictationProgressEntity(
            originalTxt = origin.originalTxt,
            progressTxt = origin.progressTxt.toCharArray()
        )
    }
}
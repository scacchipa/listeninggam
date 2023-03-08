package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.SavedDictationGameDto
import ar.com.westsoft.listening.data.engine.GameEntity
import javax.inject.Inject

class SavedDictationGameMapper @Inject constructor(
    private val dictationProgressMapper: DictationProgressMapper
) : Mapper<GameEntity, SavedDictationGameDto> {
    override fun toDataSource(origin: GameEntity): SavedDictationGameDto {
        return SavedDictationGameDto(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress,
            dictationProgressDto = dictationProgressMapper
                .toDataSource(origin.dictationProgressEntity)
        )
    }

    override fun toEngine(origin: SavedDictationGameDto): GameEntity {
        return GameEntity(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress,
            dictationProgressEntity = dictationProgressMapper.toEngine(origin.dictationProgressDto)
        )
    }


}
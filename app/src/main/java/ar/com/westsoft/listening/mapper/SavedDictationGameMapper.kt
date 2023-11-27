package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.SavedDictationGameEntity
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.mapper.toEngine
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.mapper.toEntity
import javax.inject.Inject

class SavedDictationGameMapper @Inject constructor() :
    Mapper<DictationGameRecord, SavedDictationGameEntity> {
    override fun toDataSource(origin: DictationGameRecord): SavedDictationGameEntity =
        SavedDictationGameEntity(
            gameHeaderEntity = origin.gameHeader.toEntity(),
            dictationProgressEntityLists = origin.dictationProgressList.toEntity()
                .onEach {
                    it.gameHeaderId = origin.gameHeader.gui
                }
        )

    override fun toEngine(origin: SavedDictationGameEntity) =
        DictationGameRecord(
            gameHeader = origin.gameHeaderEntity.toEngine(),
            dictationProgressList = origin.dictationProgressEntityLists.toEngine()
        )
}



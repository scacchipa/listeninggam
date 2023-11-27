package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.SavedDictationGameEntity
import ar.com.westsoft.listening.data.game.DictationGameRecord
import javax.inject.Inject

class SavedDictationGameMapper @Inject constructor(
    private val dictationProgressListMapper: DictationProgressListMapper,
    private val gameHeaderMapper: GameHeaderMapper
) : Mapper<DictationGameRecord, SavedDictationGameEntity> {
    override fun toDataSource(origin: DictationGameRecord) : SavedDictationGameEntity =
        SavedDictationGameEntity(
            gameHeaderEntity = gameHeaderMapper.toDataSource(origin.gameHeader),
            dictationProgressEntityLists = dictationProgressListMapper
                .toDataSource( origin.dictationProgressList)
                .onEach {
                    it.gameHeaderId = origin.gameHeader.gui
                }
        )

    override fun toEngine(origin: SavedDictationGameEntity) =
        DictationGameRecord(
            gameHeader = gameHeaderMapper.toEngine(origin.gameHeaderEntity),
            dictationProgressList = dictationProgressListMapper
                .toEngine(origin.dictationProgressEntityLists)
        )

}

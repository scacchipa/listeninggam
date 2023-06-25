package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.SavedDictationGameEntity
import ar.com.westsoft.listening.data.engine.Game
import javax.inject.Inject

class SavedDictationGameMapper @Inject constructor(
    private val dictationProgressListMapper: DictationProgressListMapper,
    private val gameHeaderMapper: GameHeaderMapper
) : Mapper<Game, SavedDictationGameEntity> {
    override fun toDataSource(origin: Game) : SavedDictationGameEntity =
        SavedDictationGameEntity(
            gameHeaderEntity = gameHeaderMapper.toDataSource(origin.gameHeader),
            dictationProgressEntityLists = dictationProgressListMapper
                .toDataSource( origin.dictationProgressList)
                .onEach {
                    it.progressId = origin.gameHeader.gui
                        ?: throw Exception("SavedDictationGameEntity Mapper Error")
                }
        )

    override fun toEngine(origin: SavedDictationGameEntity) =
        Game(
            gameHeader = gameHeaderMapper.toEngine(origin.gameHeaderEntity),
            dictationProgressList = dictationProgressListMapper
                .toEngine(origin.dictationProgressEntityLists)
        )

}

package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.GameHeaderEntity
import ar.com.westsoft.listening.data.engine.DictationGameHeader
import javax.inject.Inject

class GameHeaderMapper @Inject constructor()
    : Mapper<DictationGameHeader, GameHeaderEntity> {
    override fun toDataSource(origin: DictationGameHeader) =
        GameHeaderEntity(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress
        )

    override fun toEngine(origin: GameHeaderEntity) =
        DictationGameHeader(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress
        )
}
package ar.com.westsoft.listening.mapper

import ar.com.westsoft.listening.data.datasource.GameHeaderEntity
import ar.com.westsoft.listening.data.engine.GameHeader
import javax.inject.Inject

class GameHeaderMapper @Inject constructor()
    : Mapper<GameHeader, GameHeaderEntity> {
    override fun toDataSource(origin: GameHeader) =
        GameHeaderEntity(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress
        )

    override fun toEngine(origin: GameHeaderEntity) =
        GameHeader(
            gui = origin.gui,
            title = origin.title,
            txtAddress = origin.txtAddress
        )
}
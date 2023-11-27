package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.domain.dictationgame.repository

import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.repository.DictationRepository
import javax.inject.Inject

class DeleteGameUseCase @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    operator fun invoke(gameHeader: DictationGameHeader): Int =
        dictationRepository.deleteGame(gameHeader)
}
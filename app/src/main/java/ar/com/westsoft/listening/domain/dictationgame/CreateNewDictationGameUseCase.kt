package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationRepository
import ar.com.westsoft.listening.data.engine.RepoTaskResponse
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus.Completed
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus.Error
import javax.inject.Inject

class CreateNewDictationGameUseCase @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    suspend operator fun invoke(title: String, url: String): GameCreationGameStatus {
        val response = dictationRepository.createADictationGame(title, url)
        return when(response) {
            is RepoTaskResponse.Completed -> Completed(response.gui)
            is RepoTaskResponse.Uncompleted -> Error
        }
    }
}

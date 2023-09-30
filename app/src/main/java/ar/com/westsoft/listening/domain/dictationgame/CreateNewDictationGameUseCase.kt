package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.repository.DictationRepository
import ar.com.westsoft.listening.data.repository.RepoTaskResponse
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus.Completed
import ar.com.westsoft.listening.screen.dictationgame.GameCreationGameStatus.Error
import javax.inject.Inject

class CreateNewDictationGameUseCase @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    suspend operator fun invoke(title: String, url: String): GameCreationGameStatus {
        return when(
            val response = dictationRepository.createADictationGame(title, url)
        ) {
            is RepoTaskResponse.Completed -> Completed(response.gui)
            is RepoTaskResponse.Uncompleted -> Error
        }
    }
}

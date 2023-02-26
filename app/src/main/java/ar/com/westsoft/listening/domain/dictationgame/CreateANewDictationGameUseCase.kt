package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationRepository
import javax.inject.Inject

class CreateANewDictationGameUseCase @Inject constructor(
    val dictationRepository: DictationRepository
) {
    suspend operator fun invoke(url: String) {
        dictationRepository.createADictationGame(url)
    }
}
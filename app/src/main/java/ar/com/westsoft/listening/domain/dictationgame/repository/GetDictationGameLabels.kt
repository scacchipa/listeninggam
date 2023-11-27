package ar.com.westsoft.listening.domain.dictationgame.repository

import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.repository.DictationRepository
import javax.inject.Inject

class GetDictationGameLabels @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    operator fun invoke(): List<DictationGameHeader> {
        return dictationRepository.getAllDictationGameLabel()
    }
}
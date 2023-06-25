package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGameHeader
import ar.com.westsoft.listening.data.engine.DictationRepository
import javax.inject.Inject

class GetDictationGameLabels @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    operator fun invoke(): List<DictationGameHeader> {
        return dictationRepository.getAllDictationGameLabel()
    }
}
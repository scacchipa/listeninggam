package ar.com.westsoft.listening.domain.dictationgame

import ar.com.westsoft.listening.data.engine.DictationGameLabel
import ar.com.westsoft.listening.data.engine.DictationRepository
import javax.inject.Inject

class GetDictationGameLabels @Inject constructor(
    private val dictationRepository: DictationRepository
) {
    operator fun invoke(): List<DictationGameLabel> {
        return dictationRepository.getAllDictationGameLabel()
    }
}
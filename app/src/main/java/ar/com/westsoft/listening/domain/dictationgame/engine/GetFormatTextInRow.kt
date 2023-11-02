package ar.com.westsoft.listening.domain.dictationgame.engine

import androidx.compose.ui.text.AnnotatedString
import ar.com.westsoft.listening.data.repository.SettingsRepository
import ar.com.westsoft.listening.util.splitInRow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetFormatTextInRow @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(charArray: CharArray): AnnotatedString {

        val settings = runBlocking { settingsRepository.getDictGameSettingFlow().first() }

        return AnnotatedString(charArray.splitInRow(settings.columnPerPage.value).concatToString())
    }
}
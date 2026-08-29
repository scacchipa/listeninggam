package ar.com.westsoft.listening.domain.dictationgame.repository

import android.content.Context
import ar.com.scacchipa.xmlparser.domain.OpenEpubUseCase
import ar.com.scacchipa.xmlparser.xhtmlfile.EpubXhtmlTag
import ar.com.scacchipa.xmlparser.xhtmlfile.tag.EpubXhtmlString
import ar.com.westsoft.listening.data.game.DictationGame
import ar.com.westsoft.listening.data.repository.DictationRepository
import ar.com.westsoft.listening.data.repository.FileRepository
import ar.com.westsoft.listening.data.repository.RepoTaskResponse
import ar.com.westsoft.listening.screen.dictationgame.navigation.FileFormat
import ar.com.westsoft.listening.screen.dictationgame.navigation.GameCreationGameStatus
import ar.com.westsoft.listening.screen.dictationgame.navigation.GameCreationGameStatus.Completed
import ar.com.westsoft.listening.screen.dictationgame.navigation.GameCreationGameStatus.Error
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CreateTxtNewDictationGameUseCase @Inject constructor(
    private val dictationRepository: DictationRepository,
    private val dictationGame: DictationGame,
    private val fileRepository: FileRepository,
    private val epubUseCase: OpenEpubUseCase,
    @param:ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(
        title: String,
        path: String,
        source: SourceFile,
        fileFormat: FileFormat
    ): GameCreationGameStatus {

        val xhtmlFile: List<EpubXhtmlTag> = when(fileFormat) {

            FileFormat.TXT -> {
                val lines = when (source) {
                    SourceFile.ASSETS ->
                        try {
                            context.assets.open(path).bufferedReader().use { it.readLines() }
                        } catch (_: Exception) {
                            return Error
                        }
                    SourceFile.RAW ->
                        context.resources.openRawResource(path.toInt()).bufferedReader().use { it.readLines() }

                    SourceFile.INTERNET -> fileRepository.downloadFile(path)?.lines()
                        ?: return Error
                }
                lines.filter { it.isNotBlank() }.map {
                    EpubXhtmlString(it)
                }
            }

            FileFormat.EPUB3 -> when (source) {
                SourceFile.RAW -> epubUseCase.invoke(path.toInt())
                SourceFile.ASSETS -> epubUseCase.invoke(path)
                SourceFile.INTERNET -> return Error
            }
        }

        return when (
            val response = dictationRepository.createADictationGameFromTxt(
                title = title,
                textLines = xhtmlFile.flatMap { it.getTextContained().lines() }.filter { it.isNotBlank() })
        ) {
            is RepoTaskResponse.Completed -> {
                dictationGame.setup(response.gui)
                Completed(response.gui)
            }

            is RepoTaskResponse.Uncompleted -> Error
        }
    }
}
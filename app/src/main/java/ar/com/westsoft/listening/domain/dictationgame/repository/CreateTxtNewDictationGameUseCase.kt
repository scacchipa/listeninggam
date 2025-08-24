package ar.com.westsoft.listening.domain.dictationgame.repository

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
    @ApplicationContext private val context: android.content.Context,
) {
    suspend operator fun invoke(
        title: String,
        path: String,
        source: SourceFile,
        fileFormat: FileFormat
    ): GameCreationGameStatus {

        val originalText = when (source) {

            SourceFile.ASSETS ->
                try {
                    context.assets.open(path).bufferedReader().use { it.readText() }
                } catch (_: Exception) {
                    return Error
                }
            SourceFile.RAW ->
               context.resources.openRawResource(path.toInt()).bufferedReader().use { it.readText() }

            SourceFile.INTERNET -> fileRepository.downloadFile(path)
                ?: return Error
        }
        return when (
            val response = dictationRepository.createADictationGameFromTxt(title, originalText)
        ) {
            is RepoTaskResponse.Completed -> {
                dictationGame.setup(response.gui)
                Completed(response.gui)
            }

            is RepoTaskResponse.Uncompleted -> Error
        }
    }
}
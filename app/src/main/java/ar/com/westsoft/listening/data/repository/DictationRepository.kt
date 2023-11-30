package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.ExternalApi
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.util.toEntity
import ar.com.westsoft.listening.util.toEngine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

// https://www.gutenberg.org/files/74/74-0.txt --> The Adventures of Tom Sawyer, by Mark Twain
class DictationRepository @Inject constructor(
    private val externalApi: ExternalApi,
    private val appDatabase: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun createADictationGame(title: String, url: String): RepoTaskResponse {

        val originalText = externalApi.downloadFile(url)
            ?: return RepoTaskResponse.Uncompleted

        val gui = withContext(ioDispatcher) {

            val gameHeader = DictationGameHeader(0, title, url, 0.0)

            appDatabase.getSavedListeningGameDao().insertGameEntity(
                DictationGameRecord(
                    gameHeader = gameHeader,
                    dictationProgressList = originalText
                        .lines()
                        .map {
                            DictationProgress(
                                progressId = null,
                                originalTxt = it
                            )
                        }
                ).toEntity()
            )
        }
        return RepoTaskResponse.Completed(gui)
    }

    suspend fun getAllDictationGameLabel(): List<DictationGameHeader> =
        withContext(ioDispatcher) {
            appDatabase.getSavedListeningGameDao().getSavedDictationGameEntityList().map { game ->
                game.toEngine().gameHeader
            }
        }

    fun deleteGame(gameHeader: DictationGameHeader): Int =
        appDatabase.getSavedListeningGameDao().deleteWholeGame(
            gameHeader.toEntity()
        )
}

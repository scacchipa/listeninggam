package ar.com.westsoft.listening.data.engine

import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.ExternalApi
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.mapper.SavedDictationGameMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


// https://www.gutenberg.org/files/74/74-0.txt --> The Adventures of Tom Sawyer, by Mark Twain
class DictationRepository @Inject constructor(
    private val externalApi: ExternalApi,
    private val appDatabase: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val savedDictationGameMapper: SavedDictationGameMapper
) {
    suspend fun createADictationGame(title: String, url: String): RepoTaskResponse {

        val originalText = externalApi.downloadFile(url)
            ?: return RepoTaskResponse.Uncompleted

        val gui = runBlocking(ioDispatcher) {

            val gameHeader = DictationGameHeader(0, title, url, 0.0)

            appDatabase.getSavedListeningGameDao().insertGameDto(
                savedDictationGameMapper.toDataSource(
                    DictationGameRecord(
                        gameHeader = gameHeader,
                        dictationProgressList = originalText
                            .lines()
                            .map {
                                DictationProgress(
                                    progressId = null,
                                    originalTxt = it)
                            }
                    )
                )
            )
        }
        return RepoTaskResponse.Completed(gui)
    }

    fun getAllDictationGameLabel(): List<DictationGameHeader> =
        runBlocking(ioDispatcher) {
            appDatabase.getSavedListeningGameDao().getSavedDictationGameDtoList().map { game ->
                DictationGameHeader(
                    gui = game.gameHeaderEntity.gui,
                    title = game.gameHeaderEntity.title,
                    progressRate = game.gameHeaderEntity.progressRate
                )
            }
        }
}

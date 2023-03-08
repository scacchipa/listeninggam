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
            appDatabase.getSavedListeningGameDao().insert(
                savedDictationGameMapper.toDataSource(
                    GameEntity(
                        gui = 0,
                        title = title,
                        txtAddress = url,
                        dictationProgressEntity = DictationProgressEntity(originalText)
                    )
                )
            )
        }
        return RepoTaskResponse.Completed(gui)
    }

    fun getAllDictationGameLabel(): List<DictationGameLabel> =
        runBlocking(ioDispatcher) {
            appDatabase.getSavedListeningGameDao().getAllSavedListeningGame().map { game ->
                DictationGameLabel(
                    gui = game.gui,
                    title = game.title,
                    progressPercent = 0.0
                )
            }
        }
}

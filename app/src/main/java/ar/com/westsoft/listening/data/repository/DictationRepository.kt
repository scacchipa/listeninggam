package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.di.IoDispatcher
import ar.com.westsoft.listening.util.toEngine
import ar.com.westsoft.listening.util.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

// https://www.gutenberg.org/files/74/74-0.txt --> The Adventures of Tom Sawyer, by Mark Twain
class DictationRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun createADictationGameFromTxt(
        title: String, textLines: List<String>
    ): RepoTaskResponse {

        return RepoTaskResponse.Completed(

            gui = withContext(ioDispatcher) {

                appDatabase.getSavedListeningGameDao().insertGameEntity(
                    DictationGameRecord(
                        gameHeader = DictationGameHeader(0, title, 0.0),
                        dictationProgressList = textLines
                            .map {
                                DictationProgress(
                                    progressId = null,
                                    originalTxt = it
                                )
                            }
                    ).toEntity()
                )
            })
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

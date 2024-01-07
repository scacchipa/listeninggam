package ar.com.westsoft.listening.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ar.com.westsoft.listening.data.game.DictationGameHeader
import ar.com.westsoft.listening.data.game.DictationGameRecord
import ar.com.westsoft.listening.data.game.DictationProgress
import ar.com.westsoft.listening.util.toEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

class SavedDictationGameDaoTest {

    private val gameHeaderEntity1 = GameHeaderEntity(234L, "aTitle1", "address1", 12.3)
    private val gameHeaderEntity2 = GameHeaderEntity(111L, "aTitle2", "address2", 11.0)
    private val gameHeaderEntity3 = GameHeaderEntity(234L, "aTitle3", "address3", 12.0)

    private val gameHeader = DictationGameHeader(0, "New title", "address", 10.3)
    private val progressList = listOf(
        DictationProgress(null, "Text one.", "____ ___.".toCharArray()),
        DictationProgress(null, "Text two.", "____ ___.".toCharArray())
    )

    private lateinit var db: AppDatabase
    private lateinit var gameDao: SavedDictationGameDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        gameDao = db.getSavedListeningGameDao()

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertHeader() {
        gameDao.insertHeader(gameHeaderEntity1)
        gameDao.insertHeader(gameHeaderEntity2)

        val actual = gameDao.getSavedDictationGameEntityList().map { it.gameHeaderEntity }
        val expected = listOf(gameHeaderEntity2, gameHeaderEntity1)

        assert(actual == expected)
    }

    @Test
    fun updateHeader() {
        gameDao.insertHeader(gameHeaderEntity1)
        gameDao.updateHeader(gameHeaderEntity3)

        val actual = gameDao.getSavedDictationGameEntityList().map { it.gameHeaderEntity }
        val expected = listOf(gameHeaderEntity3)

        assert(actual == expected)
    }

    @Test
    fun deleteHeader() {
        gameDao.insertHeader(gameHeaderEntity1)
        gameDao.insertHeader(gameHeaderEntity2)
        gameDao.deleteWholeGame(gameHeaderEntity1)

        val actual = gameDao.getSavedDictationGameEntityList().map { it.gameHeaderEntity }
        val expected = listOf(gameHeaderEntity2)

        assert(actual == expected)
    }

    @Test
    fun insertGameEntity() {
        val gui = gameDao.insertGameEntity(
            game = DictationGameRecord(
                gameHeader = gameHeader,
                dictationProgressList = progressList
            ).toEntity()
        )

        val actual = gameDao.getSavedDictationGameEntityList()
        val expected = listOf(
            SavedDictationGameEntity(
                GameHeaderEntity(gui, "New title", "address", 10.3),
                listOf(
                    DictationProgressEntity(1, gui, "Text one.", "____ ___."),
                    DictationProgressEntity(2, gui, "Text two.", "____ ___.")
                )
            )
        )

        assert(actual == expected)
    }


    @Test
    fun insertGameHeaderAndProgress() {
        val actualGui = gameDao.insertGameHeaderAndProgress(
            gameHeaderEntity = gameHeaderEntity1,
            progressEntityList = progressList.toEntity()
        )
        val expectedGui = 234L
        val actual = gameDao.getSavedDictationGameEntityList()
        val expected = listOf(
            SavedDictationGameEntity(
                gameHeaderEntity = gameHeaderEntity1,
                dictationProgressEntityLists =
                listOf(
                    DictationProgressEntity(1, gameHeaderEntity1.gui, "Text one.", "____ ___."),
                    DictationProgressEntity(2, gameHeaderEntity1.gui, "Text two.", "____ ___.")
                )
            )
        )

        assert(actualGui == expectedGui)
        assert(actual == expected)
    }

    @Test
    fun deleteWhole() {
        gameDao.insertGameEntity(
            game = SavedDictationGameEntity(
                gameHeaderEntity = gameHeader.toEntity(),
                dictationProgressEntityLists = progressList.toEntity()
            )
        )

        gameDao.deleteWholeGame(
            GameHeaderEntity(1, "New title", "address", 10.3)
        )

        assert(gameDao.getSavedDictationGameEntityList().isEmpty())
    }
}

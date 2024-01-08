package ar.com.westsoft.listening.data.repository

import ar.com.westsoft.listening.data.datasource.AppDatabase
import ar.com.westsoft.listening.data.datasource.DictationProgressEntity
import ar.com.westsoft.listening.data.datasource.ExternalApi
import ar.com.westsoft.listening.data.datasource.GameHeaderEntity
import ar.com.westsoft.listening.data.datasource.SavedDictationGameDao
import ar.com.westsoft.listening.data.datasource.SavedDictationGameEntity
import ar.com.westsoft.listening.data.game.DictationGameHeader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock

class DictationRepositoryTest {

    private val mockExternalApi = mock<ExternalApi>()
    private val mockAppDatabase = mock<AppDatabase>()
    private val mockGameDao = mock<SavedDictationGameDao>()

    private val testDispatcher = StandardTestDispatcher()

    private val subject = DictationRepository(mockExternalApi, mockAppDatabase, testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun subjectCreateADictationGame_successfully() = runTest {
        `when`(mockAppDatabase.getSavedListeningGameDao()).thenReturn(mockGameDao)

        `when`(mockExternalApi.downloadFile("www.webpage.com"))
            .thenReturn("$paragraph1\n$paragraph2\n$paragraph3")

        val gameCapture = argumentCaptor<SavedDictationGameEntity>()
        `when`(mockGameDao.insertGameEntity(gameCapture.capture())).thenReturn(12)

        val actual = subject.createADictationGame(title = "Title 1", url = "www.webpage.com")
        val expected = RepoTaskResponse.Completed(12)

        val actualGame = gameCapture.firstValue

        with(actualGame) {
            with(gameHeaderEntity) {
                assert(gui == 0L)
                assert(title == "Title 1")
                assert(txtAddress == "www.webpage.com")
                assert(progressRate == 0.0)
            }
            with(dictationProgressEntityLists) {
                assert(size == 3)
                with(this[0]) {
                    assert(progressId == null)
                    assert(gameHeaderId == 0L)
                    assert(originalTxt == paragraph1)
                    assert(progressTxt == progress1)
                }
                with(this[1]) {
                    assert(progressId == null)
                    assert(gameHeaderId == 0L)
                    assert(originalTxt == paragraph2)
                    assert(progressTxt == progress2)
                }
                with(this[2]) {
                    assert(progressId == null)
                    assert(gameHeaderId == 0L)
                    assert(originalTxt == paragraph3)
                    assert(progressTxt == progress3)
                }
            }
        }

        assert(actual == expected)
    }

    @Test
    fun subjectCreateADictationGame_UnCompleted() = runTest {
        `when`(mockExternalApi.downloadFile("www.webpage.com")).thenReturn(null)

        val actual = subject.createADictationGame(title = "Title 1", url = "www.webpage.com")
        val expected = RepoTaskResponse.Uncompleted

        assert(actual == expected)
    }

    @Test
    fun subjectGetAllDictationGameLabel_success() = runTest {
        `when`(mockAppDatabase.getSavedListeningGameDao()).thenReturn(mockGameDao)
        `when`(mockGameDao.getSavedDictationGameEntityList()).thenReturn(
            savedDictationGameEntityList
        )

        val actual = subject.getAllDictationGameLabel()
        val expect = listOf(
            DictationGameHeader(
                gui = 2L,
                title = "Title 1",
                txtAddress = "www.webpage.com",
                progressRate = 12.3
            )
        )

        assert(actual.size == 1)
        assert(actual[0] == expect[0])
    }

    @Test
    fun subjectDeleteGame_success() {
        `when`(mockAppDatabase.getSavedListeningGameDao()).thenReturn(mockGameDao)
        `when`(
            mockGameDao.deleteWholeGame(GameHeaderEntity(4L, "Title 1", "www.webpage.com", 12.3))
        ).thenReturn(4)

        val actual = subject.deleteGame(DictationGameHeader(4L, "Title 1", "www.webpage.com", 12.3))
        val expect = 4

        assert(actual == expect)
    }

    private val paragraph1 =
        "¡Claro, puedo ayudarte con eso! El \"texto simulado\" suele referirse a texto ficticio utilizado como relleno para propósitos de diseño, desarrollo web o pruebas de formato."
    private val paragraph2 =
        "En el ámbito de la maquetación, Lorem Ipsum es un texto simulado estándar usado como relleno. Se compone de palabras latinas sin un significado real, lo que permite visualizar cómo se verá un diseño sin distraerse por el contenido real."
    private val paragraph3 =
        "Si necesitas un texto simulado para tu proyecto, existen generadores en línea que te ofrecen variaciones de Lorem Ipsum o texto similar para adaptarse a tus necesidades de diseño. ¿Te gustaría saber más sobre alguna herramienta en particular o cómo utilizar texto simulado en un proyecto específico?"

    private val progress1 =
        "¡_____, _____ ________ ___ ___! __ \"_____ ________\" _____ _________ _ _____ ________ _________ ____ _______ ____ __________ __ ______, __________ ___ _ _______ __ _______."
    private val progress2 =
        "__ __ ______ __ __ ___________, _____ _____ __ __ _____ ________ ________ _____ ____ _______. __ _______ __ ________ _______ ___ __ ___________ ____, __ ___ _______ __________ ____ __ ____ __ ______ ___ __________ ___ __ _________ ____."
    private val progress3 =
        "__ _________ __ _____ ________ ____ __ ________, _______ ___________ __ _____ ___ __ _______ ___________ __ _____ _____ _ _____ _______ ____ _________ _ ___ ___________ __ ______. ¿__ ________ _____ ___ _____ ______ ___________ __ __________ _ ____ ________ _____ ________ __ __ ________ __________?"

    private val dictationProgressEntityList = listOf(
        DictationProgressEntity(null, 0L, paragraph1, progress1),
        DictationProgressEntity(null, 0L, paragraph2, progress2),
        DictationProgressEntity(null, 0L, paragraph3, progress3)
    )

    private val gameHeaderEntity = GameHeaderEntity(2L, "Title 1", "www.webpage.com", 12.3)

    private val savedDictationGameEntity = SavedDictationGameEntity(
        gameHeaderEntity, dictationProgressEntityList
    )

    private val savedDictationGameEntityList = listOf(savedDictationGameEntity)
}

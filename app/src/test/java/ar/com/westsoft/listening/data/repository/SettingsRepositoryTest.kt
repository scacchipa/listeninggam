package ar.com.westsoft.listening.data.repository

import androidx.datastore.preferences.core.Preferences
import ar.com.westsoft.listening.data.datasource.DictGameSettingsDSO
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ColumnPerPage
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordAfterCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ReadWordBeforeCursor
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeechRatePercentage
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeedLevel
import ar.com.westsoft.listening.screen.dictationgame.settings.DictGameSettings
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource.SpeedLevelPreference.MEDIUM_SPEED_LEVEL
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify

class SettingsRepositoryTest {
    private lateinit var subject: SettingsRepository

    private val mockDataStore = mock<DictSettingsDataStore>() {
        on { getDictGameSettingsDSOFlow() } doReturn flow { emit(settingsDSO) }
        onBlocking { save(any(), any<Any>()) } doReturn mockPreferences

    }
    private val mockPreferences = mock<Preferences>()

    @Before
    fun setUp() {
        subject = SettingsRepository(mockDataStore)
    }

    @Test
    fun subjectGetDictGameSettingFlow() = runTest {
        val actual = subject.getDictGameSettingFlow().toList().toList()
        val expected = listOf(settings)

        assertEquals(actual, expected)
    }

    @Test
    fun subjectGetValues() = runTest {
        assertEquals(subject.getReadWordAfterCursor().toList(), listOf(5))
        assertEquals(subject.getReadWordBeforeCursor().toList(), listOf(6))
        assertEquals(subject.getSpeechRate().toList(), listOf(7f))
        assertEquals(subject.getSpeedLevel().toList(), listOf(MEDIUM_SPEED_LEVEL))
        assertEquals(subject.getColumnPerPage().toList(), listOf(40))

        reset(mockDataStore)
    }

    @Test
    fun subjectSaveValue() = runTest {
        assert(subject.setReadWordAfterCursor(5))
        verify(mockDataStore).save(ReadWordAfterCursor, 5)

        assert(subject.setReadWordBeforeCursor(6))
        verify(mockDataStore).save(ReadWordBeforeCursor, 6)

        assert(subject.setSpeechRate(7f))
        verify(mockDataStore).save(SpeechRatePercentage, 7f)

        assert(subject.setSpeedLevel(MEDIUM_SPEED_LEVEL))
        verify(mockDataStore).save(SpeedLevel, MEDIUM_SPEED_LEVEL.name)

        assert(subject.setColumnPerPage(40))
        verify(mockDataStore).save(ColumnPerPage, 40)

        reset(mockDataStore)
    }


    private val settingsDSO = DictGameSettingsDSO(
        readWordAfterCursor = 5,
        readWordBeforeCursor = 6,
        speechRate = 7f,
        speedLevel = MEDIUM_SPEED_LEVEL,
        columnPerPage = 40
    )

    private val settings = DictGameSettings(
        readWordAfterCursor = 5,
        readWordBeforeCursor = 6,
        speechRatePercentage = 7f,
        speedLevel = MEDIUM_SPEED_LEVEL,
        columnPerPage = 40
    )
}

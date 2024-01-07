package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import ar.com.westsoft.listening.data.datasource.DictSettingsDataStoreUtils.createMockDefaultDataStore
import ar.com.westsoft.listening.data.datasource.PreferencesKey.ColumnPerPage
import ar.com.westsoft.listening.data.datasource.PreferencesKey.SpeedLevel
import ar.com.westsoft.listening.data.datasource.SpeedLevelPreference
import ar.com.westsoft.listening.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.argumentCaptor

class DictSettingsDataStoreTest {

    private val mockDataStore = createMockDefaultDataStore()

    private lateinit var subject: DictSettingsDataStore

    @Before
    fun setup() {
        subject = DictSettingsDataStore(mockDataStore)
    }

    @Test
    fun getDictGameSettingsDSOFlow_Happy_Path_Test() = runTest {
        val actualResult = subject.getDictGameSettingsDSOFlow().first()
        val expectResult = Constants.DICT_SETTINGS_DATA_STORE_DEFAULT

        assert(actualResult == expectResult)
    }

    @Test
    fun getDictGameSettingsDSOFlow_get_a_value_Test() = runTest {
        val actualResult = subject.get(ColumnPerPage)
        val expectResult = ColumnPerPage.defaultValue

        assert(actualResult == expectResult)
    }

    @Test
    fun dictSettingsDataStore_get_a_default_when_null_Test() = runTest {
        val mockPreferences: Preferences = mock()
        `when`(mockPreferences[ColumnPerPage]).thenReturn(null)

        val mockDataStore = createMockDefaultDataStore(mockPreferences)

        val subject = DictSettingsDataStore(mockDataStore)

        val actualResult = subject.get(ColumnPerPage)
        val expectResult = ColumnPerPage.defaultValue

        assert(actualResult == expectResult)
    }

    @Test
    fun dictSettingsDataStore_save_data() = runTest {

        val mockDataStore: DataStore<Preferences> = mock()

        val captorUpdate =  argumentCaptor<suspend (Preferences) -> Preferences>()
        `when`(mockDataStore.updateData(transform = captorUpdate.capture())).thenReturn(
            preferencesOf( intPreferencesKey("Number") to 123)
        )

        val subject = DictSettingsDataStore(mockDataStore)

        val subjectReturn = subject.save(
            preferenceField = SpeedLevel,
            value = SpeedLevelPreference.MEDIUM_SPEED_LEVEL.name
        )

        val actualReturn = subjectReturn[intPreferencesKey("Number")]
        val expectedReturn = 123

        val actualUpdate = captorUpdate.firstValue.invoke(preferencesOf())[SpeedLevel]
        val expectedUpdate = SpeedLevelPreference.MEDIUM_SPEED_LEVEL.name

        assert(actualReturn == expectedReturn)
        assert(actualUpdate == expectedUpdate)
    }
}

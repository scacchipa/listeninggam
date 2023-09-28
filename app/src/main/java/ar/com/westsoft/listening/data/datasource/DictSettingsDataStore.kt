package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.screen.dictationgame.DictGameSettingsDSO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictSettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun getDictGameSettingsDSOFlow(): Flow<DictGameSettingsDSO> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .onEach {
                println("Preferences changed")
            }
            .map { preferences ->
                preferences.getDictGameSettingsDSO()
            }

    suspend fun setReadWordAfterCursor(value: Int) {
        saveValueWithKey(value, PreferencesKeys.READ_WORD_AFTER_CURSOR)
    }

    suspend fun setReadWordBeforeCursor(value: Int) {
        saveValueWithKey(value, PreferencesKeys.READ_WORD_BEFORE_CURSOR)
    }

    private suspend fun <T> saveValueWithKey(value: T, key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}

object PreferencesKeys {
    val READ_WORD_AFTER_CURSOR = intPreferencesKey("read_word_after_cursor")
    val READ_WORD_BEFORE_CURSOR = intPreferencesKey("read_word_before_cursor")
}

fun Preferences.getDictGameSettingsDSO() = DictGameSettingsDSO(
    readWordAfterCursorValue = this[PreferencesKeys.READ_WORD_AFTER_CURSOR] ?: 7,
    readWordBeforeCursorValue = this[PreferencesKeys.READ_WORD_BEFORE_CURSOR] ?: 2
)

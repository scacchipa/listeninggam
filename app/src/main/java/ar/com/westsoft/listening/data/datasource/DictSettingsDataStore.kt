package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictSettingsDataStore @Inject constructor(
    val dataStore: DataStore<Preferences>
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

    suspend inline fun <reified T> get(preferenceField: PreferencesKey<T>): T {
        return dataStore.data.first()[preferenceField.key]
            ?: preferenceField.defaultValue
    }

    suspend inline fun <reified T> save(preferenceField: PreferencesKey<T>, value: T?) {
        dataStore.edit { preferences ->
            preferences[preferenceField.key] = value ?: preferenceField.defaultValue
        }
    }
}

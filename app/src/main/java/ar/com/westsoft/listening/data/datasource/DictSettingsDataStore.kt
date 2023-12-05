package ar.com.westsoft.listening.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictSettingsDataStore @Inject constructor(
    val dataStore: DataStore<Preferences>
) {
    fun getDictGameSettingsDSOFlow(): Flow<DictGameSettingsDSO> =
        dataStore.data
            .map { preferences ->
                preferences.getDictGameSettingsDSO()
            }

    suspend fun <T> get(preferenceField: PreferencesKey<T>): T {
        return dataStore.data.first()[preferenceField.key]
            ?: preferenceField.defaultValue
    }

    suspend inline fun <T> save(preferenceField: PreferencesKey<T>, value: T?) =
        dataStore.edit { preferences ->
            preferences[preferenceField.key] = value ?: preferenceField.defaultValue
        }
}

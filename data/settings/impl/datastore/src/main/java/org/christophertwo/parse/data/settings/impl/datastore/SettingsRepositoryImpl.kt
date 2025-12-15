package org.christophertwo.parse.data.settings.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.christophertwo.parse.data.settings.api.PreferencesKeys
import org.christophertwo.parse.data.settings.api.SettingsRepository
import java.io.IOException

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    override fun darkTheme(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.DARK_THEME] ?: true
        }

    override suspend fun setDarkTheme(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = value
        }
    }

    override fun themeSystem(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.THEME_SYSTEM] ?: true
        }

    override suspend fun setThemeSystem(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_SYSTEM] = value
        }
    }
}

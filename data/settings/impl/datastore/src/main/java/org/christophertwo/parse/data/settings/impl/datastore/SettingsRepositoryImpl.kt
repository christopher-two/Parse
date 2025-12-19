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
import org.christophertwo.parse.domain.models.settings.Contrast
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

    override fun fontSize(): Flow<Float> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())            
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] ?: 1.0f
        }

    override suspend fun setFontSize(size: Float) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FONT_SIZE] = size
        }
    }

    override fun themeColor(): Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.THEME_COLOR] ?: 0xFF009688.toInt()
        }

    override suspend fun setThemeColor(color: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_COLOR] = color
        }
    }

    override fun isAmoledTheme(): Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.AMOLED_THEME] ?: false
        }

    override suspend fun setAmoledTheme(isAmoled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AMOLED_THEME] = isAmoled
        }
    }

    override fun contrast(): Flow<Contrast> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val contrastName = preferences[PreferencesKeys.CONTRAST]
            try {
                if (contrastName != null) Contrast.valueOf(contrastName) else Contrast.MEDIUM
            } catch (e: IllegalArgumentException) {
                Contrast.MEDIUM
            }
        }

    override suspend fun setContrast(contrast: Contrast) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.CONTRAST] = contrast.name
        }
    }
}

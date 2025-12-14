package org.christophertwo.parse.data.settings.impl.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.firstOrNull
import org.christophertwo.parse.data.settings.api.PreferencesKeys
import org.christophertwo.parse.data.settings.api.SettingsRepository

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {
    override suspend fun darkTheme(): Result<Boolean> {
        return try {
            val darkTheme = dataStore.data.firstOrNull()?.get(PreferencesKeys.DARK_THEME) ?: true
            Result.success(darkTheme)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun setDarkTheme(value: Boolean): Result<Unit> {
        return try {
            dataStore.updateData { preferences ->
                preferences.toMutablePreferences().apply {
                    this[PreferencesKeys.DARK_THEME] = value
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
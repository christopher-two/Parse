package org.christophertwo.parse.data.settings.api

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun darkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(value: Boolean)
    fun themeSystem(): Flow<Boolean>

    suspend fun setThemeSystem(value: Boolean)
}

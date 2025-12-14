package org.christophertwo.parse.data.settings.api

interface SettingsRepository {
    suspend fun darkTheme(): Result<Boolean>

    suspend fun setDarkTheme(value: Boolean): Result<Unit>
}
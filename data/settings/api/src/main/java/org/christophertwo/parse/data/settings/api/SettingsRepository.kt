package org.christophertwo.parse.data.settings.api

import kotlinx.coroutines.flow.Flow
import org.christophertwo.parse.domain.models.settings.Contrast

interface SettingsRepository {
    fun darkTheme(): Flow<Boolean>

    suspend fun setDarkTheme(value: Boolean)
    fun themeSystem(): Flow<Boolean>

    suspend fun setThemeSystem(value: Boolean)

    fun fontSize(): Flow<Float>

    suspend fun setFontSize(size: Float)

    fun themeColor(): Flow<Int>

    suspend fun setThemeColor(color: Int)

    fun isAmoledTheme(): Flow<Boolean>

    suspend fun setAmoledTheme(isAmoled: Boolean)

    fun contrast(): Flow<Contrast>

    suspend fun setContrast(contrast: Contrast)
}

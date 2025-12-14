package org.christophertwo.parse.feature.settings.domain

import kotlinx.coroutines.flow.Flow
import org.christophertwo.parse.data.settings.api.SettingsRepository

class IsDarkThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return settingsRepository.darkTheme()
    }
}

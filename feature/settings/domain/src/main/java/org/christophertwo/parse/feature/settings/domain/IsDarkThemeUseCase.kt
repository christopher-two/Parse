package org.christophertwo.parse.feature.settings.domain

import org.christophertwo.parse.data.settings.api.SettingsRepository

class IsDarkThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Boolean {
        settingsRepository.darkTheme()
            .onSuccess {
                return it
            }
            .onFailure {
                return false
            }
        return false
    }
}
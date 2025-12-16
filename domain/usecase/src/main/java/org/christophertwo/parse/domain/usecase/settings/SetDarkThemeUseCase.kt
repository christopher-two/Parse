package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository

class SetDarkThemeUseCase(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(
        darkTheme: Boolean,
    ) {
        settingsRepository.setDarkTheme(darkTheme)
    }
}
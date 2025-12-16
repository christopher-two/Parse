package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository

class SetThemeSystemUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(value: Boolean) {
        return settingsRepository.setThemeSystem(value)
    }
}

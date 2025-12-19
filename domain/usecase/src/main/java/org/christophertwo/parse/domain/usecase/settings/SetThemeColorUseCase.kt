package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository

class SetThemeColorUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(color: Int) {
        settingsRepository.setThemeColor(color)
    }
}

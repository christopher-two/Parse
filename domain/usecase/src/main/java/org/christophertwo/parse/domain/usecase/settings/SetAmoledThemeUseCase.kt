package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository

class SetAmoledThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(isAmoled: Boolean) {
        settingsRepository.setAmoledTheme(isAmoled)
    }
}

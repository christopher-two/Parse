package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository

class SetFontSizeUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(size: Float) {
        settingsRepository.setFontSize(size)
    }
}

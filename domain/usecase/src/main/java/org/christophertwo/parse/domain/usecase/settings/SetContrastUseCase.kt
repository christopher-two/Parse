package org.christophertwo.parse.domain.usecase.settings

import org.christophertwo.parse.data.settings.api.SettingsRepository
import org.christophertwo.parse.domain.models.settings.Contrast

class SetContrastUseCase(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(contrast: Contrast) {
        settingsRepository.setContrast(contrast)
    }
}

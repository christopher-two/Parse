package org.christophertwo.parse.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import org.christophertwo.parse.data.settings.api.SettingsRepository
import org.christophertwo.parse.domain.models.settings.Contrast

class GetContrastUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Contrast> {
        return settingsRepository.contrast()
    }
}

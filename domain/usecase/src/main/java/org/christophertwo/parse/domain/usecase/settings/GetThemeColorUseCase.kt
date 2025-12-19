package org.christophertwo.parse.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import org.christophertwo.parse.data.settings.api.SettingsRepository

class GetThemeColorUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Int> {
        return settingsRepository.themeColor()
    }
}

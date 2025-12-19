package org.christophertwo.parse.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import org.christophertwo.parse.data.settings.api.SettingsRepository

class IsAmoledThemeUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return settingsRepository.isAmoledTheme()
    }
}

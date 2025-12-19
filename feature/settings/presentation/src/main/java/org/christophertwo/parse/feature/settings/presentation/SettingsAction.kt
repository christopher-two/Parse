package org.christophertwo.parse.feature.settings.presentation

import org.christophertwo.parse.domain.models.settings.Contrast

sealed interface SettingsAction {
    object IncreaseFontSize : SettingsAction
    object DecreaseFontSize : SettingsAction
    data class SetDarkTheme(val isDarkTheme: Boolean) : SettingsAction
    data class SetThemeSystem(val useSystemTheme: Boolean) : SettingsAction
    data class SetFontSize(val fontSize: Float) : SettingsAction
    data class SetThemeColor(val color: Int) : SettingsAction
    data class SetAmoledTheme(val isAmoled: Boolean) : SettingsAction
    data class SetContrast(val contrast: Contrast) : SettingsAction
}
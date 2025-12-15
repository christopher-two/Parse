package org.christophertwo.parse.feature.settings.presentation

sealed interface SettingsAction {
    object IncreaseFontSize : SettingsAction
    object DecreaseFontSize : SettingsAction
    data class SetDarkTheme(val isDarkTheme: Boolean) : SettingsAction
    data class SetThemeSystem(val useSystemTheme: Boolean) : SettingsAction
}
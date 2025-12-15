package org.christophertwo.parse.feature.settings.presentation

data class SettingsState(
    val darkTheme: Boolean = false,
    val themeSystem: Boolean = true,
    val fontSize: Int = 16,
)

package org.christophertwo.parse.feature.settings.presentation

import org.christophertwo.parse.domain.models.settings.Contrast

data class SettingsState(
    val darkTheme: Boolean = false,
    val themeSystem: Boolean = true,
    val fontSize: Float = 1.0f,
    val themeColor: Int = 0xFF009688.toInt(),
    val amoledTheme: Boolean = false,
    val contrast: Contrast = Contrast.MEDIUM,
)

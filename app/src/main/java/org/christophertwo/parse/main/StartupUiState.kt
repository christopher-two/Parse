package org.christophertwo.parse.main

import org.christophertwo.parse.domain.models.settings.Contrast

sealed interface StartupUiState {
    data object Loading : StartupUiState

    data class Success(
        val isDarkTheme: Boolean,
        val useSystemTheme: Boolean,
        val fontSize: Float,
        val themeColor: Int,
        val isAmoledTheme: Boolean,
        val contrast: Contrast,
    ) : StartupUiState
}
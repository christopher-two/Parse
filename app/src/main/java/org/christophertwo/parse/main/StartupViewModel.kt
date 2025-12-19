package org.christophertwo.parse.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.christophertwo.parse.domain.models.settings.Contrast
import org.christophertwo.parse.domain.usecase.settings.GetContrastUseCase
import org.christophertwo.parse.domain.usecase.settings.GetFontSizeUseCase
import org.christophertwo.parse.domain.usecase.settings.GetThemeColorUseCase
import org.christophertwo.parse.domain.usecase.settings.IsAmoledThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsThemeSystemUseCase

class StartupViewModel(
    isDarkThemeUseCase: IsDarkThemeUseCase,
    isThemeSystemUseCase: IsThemeSystemUseCase,
    getFontSizeUseCase: GetFontSizeUseCase,
    getThemeColorUseCase: GetThemeColorUseCase,
    isAmoledThemeUseCase: IsAmoledThemeUseCase,
    getContrastUseCase: GetContrastUseCase,
) : ViewModel() {

    val uiState: StateFlow<StartupUiState> = combine(
        isDarkThemeUseCase(),
        isThemeSystemUseCase(),
        getFontSizeUseCase(),
        getThemeColorUseCase(),
        isAmoledThemeUseCase(),
        getContrastUseCase(),
    ) { values: Array<Any?> ->
        val isDarkTheme = values.getOrNull(0) as? Boolean ?: true
        val useSystemTheme = values.getOrNull(1) as? Boolean ?: true
        val fontSize = values.getOrNull(2) as? Float ?: 1.0f
        val themeColor = values.getOrNull(3) as? Int ?: 0xFF009688.toInt()
        val isAmoledTheme = values.getOrNull(4) as? Boolean ?: false
        val contrast = values.getOrNull(5) as? Contrast ?: Contrast.MEDIUM

        StartupUiState.Success(
            isDarkTheme = isDarkTheme,
            useSystemTheme = useSystemTheme,
            fontSize = fontSize,
            themeColor = themeColor,
            isAmoledTheme = isAmoledTheme,
            contrast = contrast,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StartupUiState.Loading
    )
}

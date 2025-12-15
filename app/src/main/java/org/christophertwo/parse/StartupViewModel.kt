package org.christophertwo.parse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.IsThemeSystemUseCase

sealed interface StartupUiState {
    data object Loading : StartupUiState
    data class Success(val isDarkTheme: Boolean, val useSystemTheme: Boolean) : StartupUiState
}

class StartupViewModel(
    isDarkThemeUseCase: IsDarkThemeUseCase,
    isThemeSystemUseCase: IsThemeSystemUseCase,
) : ViewModel() {
    val uiState: StateFlow<StartupUiState> = combine(
        isDarkThemeUseCase(),
        isThemeSystemUseCase()
    ) { isDarkTheme, useSystemTheme ->
        StartupUiState.Success(isDarkTheme, useSystemTheme)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StartupUiState.Loading
    )
}

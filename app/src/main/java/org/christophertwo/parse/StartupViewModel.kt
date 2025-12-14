package org.christophertwo.parse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase

sealed interface StartupUiState {
    data object Loading : StartupUiState
    data class Success(val isDarkTheme: Boolean) : StartupUiState
}

class StartupViewModel(
    isDarkThemeUseCase: IsDarkThemeUseCase
) : ViewModel() {
    val uiState: StateFlow<StartupUiState> = isDarkThemeUseCase()
        .map { isDarkTheme -> StartupUiState.Success(isDarkTheme) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StartupUiState.Loading
        )
}

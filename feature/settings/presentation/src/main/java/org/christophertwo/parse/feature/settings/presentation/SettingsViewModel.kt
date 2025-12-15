package org.christophertwo.parse.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.IsThemeSystemUseCase
import org.christophertwo.parse.feature.settings.domain.SetDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.SetThemeSystemUseCase

class SettingsViewModel(
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val isThemeSystemUseCase: IsThemeSystemUseCase,
    private val setThemeSystemUseCase: SetThemeSystemUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .asStateFlow()
        .stateIn(
            viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsState()
        )

    init {
        combine(
            isDarkThemeUseCase(),
            isThemeSystemUseCase(),
        ) { isDarkTheme, useSystemTheme ->
            _state.update {
                it.copy(
                    darkTheme = isDarkTheme,
                    themeSystem = useSystemTheme
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.SetDarkTheme -> {
                viewModelScope.launch {
                    setDarkThemeUseCase(action.isDarkTheme)
                }
            }

            is SettingsAction.SetThemeSystem -> {
                viewModelScope.launch {
                    setThemeSystemUseCase(action.useSystemTheme)
                }
            }

            SettingsAction.DecreaseFontSize -> {
                _state.update {
                    it.copy(fontSize = it.fontSize - 1)
                }
            }

            SettingsAction.IncreaseFontSize -> {
                _state.update {
                    it.copy(fontSize = it.fontSize + 1)
                }
            }
        }
    }
}

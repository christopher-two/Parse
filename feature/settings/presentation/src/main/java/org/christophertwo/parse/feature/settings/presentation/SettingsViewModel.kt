package org.christophertwo.parse.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.SetDarkThemeUseCase

class SettingsViewModel(
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(SettingsState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                _state.update { it.copy(darkTheme = isDarkThemeUseCase.invoke()) }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.DecreaseFontSize -> TODO()
            SettingsAction.IncreaseFontSize -> TODO()
            is SettingsAction.SetDarkTheme -> {
                viewModelScope.launch(Dispatchers.IO) {
                    setDarkThemeUseCase.invoke(action.isDarkTheme)
                    _state.update { it.copy(darkTheme = action.isDarkTheme) }
                }
            }
        }
    }
}
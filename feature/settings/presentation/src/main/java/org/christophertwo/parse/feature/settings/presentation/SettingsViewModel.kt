package org.christophertwo.parse.feature.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.christophertwo.parse.feature.settings.domain.IsDarkThemeUseCase
import org.christophertwo.parse.feature.settings.domain.SetDarkThemeUseCase

class SettingsViewModel(
    private val isDarkThemeUseCase: IsDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
) : ViewModel() {

    val state: StateFlow<SettingsState> = isDarkThemeUseCase()
        .map { isDarkTheme ->
            SettingsState(darkTheme = isDarkTheme)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = SettingsState()
        )

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.SetDarkTheme -> {
                viewModelScope.launch {
                    setDarkThemeUseCase(action.isDarkTheme)
                }
            }

            else -> {
                // TODO
            }
        }
    }
}

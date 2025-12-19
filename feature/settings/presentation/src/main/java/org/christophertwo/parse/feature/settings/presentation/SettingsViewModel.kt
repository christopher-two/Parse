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
import org.christophertwo.parse.domain.models.settings.Contrast
import org.christophertwo.parse.domain.usecase.settings.GetContrastUseCase
import org.christophertwo.parse.domain.usecase.settings.GetFontSizeUseCase
import org.christophertwo.parse.domain.usecase.settings.GetThemeColorUseCase
import org.christophertwo.parse.domain.usecase.settings.IsAmoledThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.IsThemeSystemUseCase
import org.christophertwo.parse.domain.usecase.settings.SetAmoledThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetContrastUseCase
import org.christophertwo.parse.domain.usecase.settings.SetDarkThemeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetFontSizeUseCase
import org.christophertwo.parse.domain.usecase.settings.SetThemeColorUseCase
import org.christophertwo.parse.domain.usecase.settings.SetThemeSystemUseCase

class SettingsViewModel(
    isDarkThemeUseCase: IsDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    isThemeSystemUseCase: IsThemeSystemUseCase,
    private val setThemeSystemUseCase: SetThemeSystemUseCase,
    getFontSizeUseCase: GetFontSizeUseCase,
    private val setFontSizeUseCase: SetFontSizeUseCase,
    getContrastUseCase: GetContrastUseCase,
    private val setContrastUseCase: SetContrastUseCase,
    getThemeColorUseCase: GetThemeColorUseCase,
    private val setThemeColorUseCase: SetThemeColorUseCase,
    isAmoledThemeUseCase: IsAmoledThemeUseCase,
    private val setAmoledThemeUseCase: SetAmoledThemeUseCase
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
            getFontSizeUseCase(),
            getThemeColorUseCase(),
            isAmoledThemeUseCase(),
            getContrastUseCase(),
        ) { values: Array<Any?> ->
            val isDarkTheme = values.getOrNull(0) as? Boolean ?: false
            val useSystemTheme = values.getOrNull(1) as? Boolean ?: true
            val fontSize = values.getOrNull(2) as? Float ?: 1.0f
            val themeColor = values.getOrNull(3) as? Int ?: 0xFF009688.toInt()
            val amoledTheme = values.getOrNull(4) as? Boolean ?: false
            val contrast = values.getOrNull(5) as? Contrast ?: Contrast.MEDIUM

            _state.update {
                it.copy(
                    darkTheme = isDarkTheme,
                    themeSystem = useSystemTheme,
                    fontSize = fontSize,
                    themeColor = themeColor,
                    amoledTheme = amoledTheme,
                    contrast = contrast,
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
                viewModelScope.launch {
                    val newSize = (_state.value.fontSize - 0.1f).coerceAtLeast(0.5f)
                    setFontSizeUseCase(newSize)
                }
            }

            SettingsAction.IncreaseFontSize -> {
                viewModelScope.launch {
                    val newSize = (_state.value.fontSize + 0.1f).coerceAtMost(2.0f)
                    setFontSizeUseCase(newSize)
                }
            }

            is SettingsAction.SetFontSize -> {
                viewModelScope.launch {
                    setFontSizeUseCase(action.fontSize)
                }
            }

            is SettingsAction.SetThemeColor -> {
                viewModelScope.launch {
                    setThemeColorUseCase(action.color)
                }
            }

            is SettingsAction.SetAmoledTheme -> {
                viewModelScope.launch {
                    setAmoledThemeUseCase(action.isAmoled)
                }
            }

            is SettingsAction.SetContrast -> {
                viewModelScope.launch {
                    setContrastUseCase(action.contrast)
                }
            }
        }
    }
}

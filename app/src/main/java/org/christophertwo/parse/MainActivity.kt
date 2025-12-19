package org.christophertwo.parse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.main.RootNavigationWrapper
import org.christophertwo.parse.main.StartupUiState
import org.christophertwo.parse.main.StartupViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: StartupViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition { uiState is StartupUiState.Loading }

            val darkTheme = shouldUseDarkTheme(uiState)

            val themeColor = (uiState as? StartupUiState.Success)?.themeColor
            val fontSize = (uiState as? StartupUiState.Success)?.fontSize ?: 1.0f
            val isAmoled = (uiState as? StartupUiState.Success)?.isAmoledTheme ?: false
            val contrast = (uiState as? StartupUiState.Success)?.contrast
                ?: org.christophertwo.parse.domain.models.settings.Contrast.MEDIUM

            ParseTheme(
                isDark = darkTheme,
                isAmoled = isAmoled,
                seedColor = Color(themeColor ?: 0xFF009688.toInt()),
                contrast = contrast,
                fontSize = fontSize,
            ) {
                RootNavigationWrapper()
            }
        }
    }

    @Composable
    private fun shouldUseDarkTheme(uiState: StartupUiState): Boolean = when (uiState) {
        is StartupUiState.Loading -> isSystemInDarkTheme()
        is StartupUiState.Success -> if (uiState.useSystemTheme) isSystemInDarkTheme() else uiState.isDarkTheme
    }
}

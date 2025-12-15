package org.christophertwo.parse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
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

            ParseTheme(
                isDark = darkTheme
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

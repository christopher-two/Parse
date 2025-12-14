package org.christophertwo.parse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
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

            val useDarkTheme = when (val state = uiState) {
                is StartupUiState.Success -> state.isDarkTheme
                is StartupUiState.Loading -> isSystemInDarkTheme()
            }

            ParseTheme(
                isDark = useDarkTheme
            ) {
                RootNavigationWrapper()
            }
        }
    }
}

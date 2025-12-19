package org.christophertwo.parse.feature.auth.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.common.route.RouteGlobal
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator
import org.koin.compose.koinInject

@Composable
fun AuthRoot(
    viewModel: AuthViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AuthScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun AuthScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(
        state.isError,
        state.isLoggedIn
    ) {
        when {
            state.isError != null -> snackbarHostState.showSnackbar(state.isError)
            state.isLoggedIn -> snackbarHostState.showSnackbar("Logged In")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            AuthContent(
                paddingValues = paddingValues,
                authState = state,
                onAction = onAction
            )
        },
        snackbarHost = {
            snackbarHostState.currentSnackbarData?.let {
                Snackbar(
                    snackbarData = it,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraExtraLarge
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AuthContent(
    paddingValues: PaddingValues,
    authState: AuthState,
    onAction: (AuthAction) -> Unit
) {
    val globalNavigator: GlobalNavigator = koinInject()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Spacer(modifier = Modifier.weight(1f))
            Crossfade(
                targetState = authState.isLoading,
                label = "Loading"
            ) {
                if (it) {
                    ContainedLoadingIndicator()
                } else {
                    Text(
                        text = "Parse",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Black
                        ),
                        color = colorScheme.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    contentColor = colorScheme.onSurface,
                    containerColor = colorScheme.surfaceContainerHigh
                ),
                onClick = {
                    globalNavigator.navigateTo(RouteGlobal.Home)
                    globalNavigator.back(RouteGlobal.Auth)
                }
            ) {
                Text(
                    text = "Continue With Google",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    ParseTheme {
        AuthScreen(
            state = AuthState(),
            onAction = {}
        )
    }
}
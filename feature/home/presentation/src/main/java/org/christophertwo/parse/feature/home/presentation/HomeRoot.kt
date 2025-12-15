package org.christophertwo.parse.feature.home.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.feature.home.presentation.components.AddBookSheet
import org.christophertwo.parse.feature.home.presentation.components.HomeContent
import org.christophertwo.parse.feature.home.presentation.components.HomeTopBar
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator
import org.christophertwo.parse.feature.navigation.navigator.HomeNavigator
import org.koin.compose.koinInject
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun HomeRoot(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val homeNavigator = koinInject<HomeNavigator>()
    val globalNavigator = koinInject<GlobalNavigator>()
    val entryProvider = koinEntryProvider()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopBar(globalNavigator) },
        content = { paddingValues -> HomeContent(paddingValues, homeNavigator, entryProvider) },
        floatingActionButton = {
            MediumFloatingActionButton(
                onClick = {
                    onAction(HomeAction.AddBook)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add book",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    )

    AddBookSheet(
        state = state,
        onAction = onAction
    )
}

@Preview
@Composable
private fun Preview() {
    ParseTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}
package org.christophertwo.parse.feature.home.presentation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MediumFloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.christophertwo.parse.core.common.route.RouteGlobal
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.feature.home.presentation.components.CoilAsyncImage
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator
import org.christophertwo.parse.feature.navigation.navigator.HomeNavigator
import org.koin.compose.koinInject
import org.koin.compose.navigation3.EntryProvider
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
    KoinExperimentalAPI::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val homeNavigator = koinInject<HomeNavigator>()
    val globalNavigator = koinInject<GlobalNavigator>()
    val entryProvider = koinEntryProvider()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onAction(HomeAction.UpdateBookImage(uri))
            }
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBarContent(globalNavigator) },
        content = { paddingValues -> Content(paddingValues, homeNavigator, entryProvider) },
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

    if (state.showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onAction(HomeAction.DismissBottomSheet) },
            sheetState = sheetState,
        ) {
            Crossfade(
                targetState = state.bookDownloaded,
                modifier = Modifier.fillMaxSize(),
                animationSpec = tween(500),
                content = { book ->
                    if (book) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {
                                Box(
                                    modifier = Modifier.clickable {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                ) {
                                    CoilAsyncImage(
                                        modifier = Modifier
                                            .size(150.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        imageUrl = state.imageBook,
                                        contentDescription = "Book cover",
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                TextField(
                                    value = state.titleBook,
                                    singleLine = true,
                                    maxLines = 1,
                                    label = { Text("Title") },
                                    colors = TextFieldDefaults.colors(
                                        focusedSupportingTextColor = Color.Transparent,
                                        unfocusedSupportingTextColor = Color.Transparent,
                                        focusedContainerColor = colorScheme.surfaceContainer,
                                        unfocusedContainerColor = colorScheme.surfaceContainer,
                                        focusedIndicatorColor = colorScheme.onSurface,
                                        unfocusedIndicatorColor = colorScheme.onSurface,
                                        focusedTextColor = colorScheme.onSurface,
                                        unfocusedTextColor = colorScheme.onSurface,
                                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        focusedLabelColor = colorScheme.onSurfaceVariant,
                                        unfocusedLabelColor = colorScheme.onSurfaceVariant,
                                    ),
                                    onValueChange = { bookTitle ->
                                        onAction(
                                            HomeAction.UpdateBookTitle(
                                                bookTitle
                                            )
                                        )
                                    },
                                )
                                TextField(
                                    value = state.authorBook,
                                    singleLine = true,
                                    maxLines = 1,
                                    label = { Text("Author") },
                                    colors = TextFieldDefaults.colors(
                                        focusedSupportingTextColor = Color.Transparent,
                                        unfocusedSupportingTextColor = Color.Transparent,
                                        focusedContainerColor = colorScheme.surfaceContainer,
                                        unfocusedContainerColor = colorScheme.surfaceContainer,
                                        focusedIndicatorColor = colorScheme.onSurface,
                                        unfocusedIndicatorColor = colorScheme.onSurface,
                                        focusedTextColor = colorScheme.onSurface,
                                        unfocusedTextColor = colorScheme.onSurface,
                                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        focusedLabelColor = colorScheme.onSurfaceVariant,
                                        unfocusedLabelColor = colorScheme.onSurfaceVariant,
                                    ),
                                    onValueChange = { bookAuthor ->
                                        onAction(
                                            HomeAction.UpdateBookAuthor(
                                                bookAuthor
                                            )
                                        )
                                    },
                                )
                                TextField(
                                    value = state.yearBook,
                                    singleLine = true,
                                    maxLines = 1,
                                    label = { Text("Year") },
                                    colors = TextFieldDefaults.colors(
                                        focusedSupportingTextColor = Color.Transparent,
                                        unfocusedSupportingTextColor = Color.Transparent,
                                        focusedContainerColor = colorScheme.surfaceContainer,
                                        unfocusedContainerColor = colorScheme.surfaceContainer,
                                        focusedIndicatorColor = colorScheme.onSurface,
                                        unfocusedIndicatorColor = colorScheme.onSurface,
                                        focusedTextColor = colorScheme.onSurface,
                                        unfocusedTextColor = colorScheme.onSurface,
                                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        focusedLabelColor = colorScheme.onSurfaceVariant,
                                        unfocusedLabelColor = colorScheme.onSurfaceVariant,
                                    ),
                                    onValueChange = { bookYear ->
                                        onAction(
                                            HomeAction.UpdateBookYear(
                                                bookYear
                                            )
                                        )
                                    },
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Button(
                                    onClick = { onAction(HomeAction.SaveBook) },
                                    enabled = !state.isSaving,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (state.isSaving) {
                                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.Save,
                                            contentDescription = "Save book"
                                        )
                                    }
                                }
                            }
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            content = {
                                TextField(
                                    value = state.bookUri,
                                    singleLine = true,
                                    maxLines = 1,
                                    label = { Text("Book URL") },
                                    colors = TextFieldDefaults.colors(
                                        focusedSupportingTextColor = Color.Transparent,
                                        unfocusedSupportingTextColor = Color.Transparent,
                                        focusedContainerColor = colorScheme.surfaceContainer,
                                        unfocusedContainerColor = colorScheme.surfaceContainer,
                                        focusedIndicatorColor = colorScheme.onSurface,
                                        unfocusedIndicatorColor = colorScheme.onSurface,
                                        focusedTextColor = colorScheme.onSurface,
                                        unfocusedTextColor = colorScheme.onSurface,
                                        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                                        focusedLabelColor = colorScheme.onSurfaceVariant,
                                        unfocusedLabelColor = colorScheme.onSurfaceVariant,
                                    ),
                                    onValueChange = { bookUri -> onAction(HomeAction.BookUri(bookUri)) },
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(onClick = { onAction(HomeAction.DownloadBook) }) {
                                        Icon(
                                            imageVector = Icons.Filled.Download,
                                            contentDescription = "Add book",
                                        )
                                    }
                                    val launcher = rememberLauncherForActivityResult(
                                        ActivityResultContracts.GetContent()
                                    ) { uri: android.net.Uri? ->
                                        uri?.let { onAction(HomeAction.BookSelected(it)) }
                                    }
                                    Button(onClick = { launcher.launch("application/pdf") }) {
                                        Text("Upload from device")
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarContent(globalNavigator: GlobalNavigator) {
    TopAppBar(
        title = { Text("Parse") },
        actions = {
            IconButton(
                onClick = {
                    globalNavigator.navigateTo(RouteGlobal.Settings)
                },
                content = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        }
    )
}

@OptIn(KoinExperimentalAPI::class)
@Composable
private fun Content(
    paddingValues: PaddingValues,
    homeNavigator: HomeNavigator,
    entryProvider: EntryProvider
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        content = {
            NavDisplay(
                backStack = homeNavigator.currentStack,
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),
                    rememberViewModelStoreNavEntryDecorator()
                ),
                onBack = { homeNavigator.back() },
                entryProvider = entryProvider,
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(250)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(250)
                    )
                },
                popTransitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { -it },
                        animationSpec = tween(250)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(250)
                    )
                }
            )
        }
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
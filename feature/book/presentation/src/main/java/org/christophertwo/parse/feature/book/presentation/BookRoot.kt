package org.christophertwo.parse.feature.book.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.FloatingToolbarHorizontalFabPosition
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.motionScheme
import androidx.compose.material3.MediumExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme

@Composable
fun BookRoot(
    viewModel: BookViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BookScreen(
    state: BookState,
    onAction: (BookAction) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookTopBar(scrollBehavior) },
        floatingActionButton = {
            HorizontalFloatingToolbar(
                expanded = expanded,
                floatingActionButton = {
                    MediumExtendedFloatingActionButton(
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit Button"
                            )
                        },
                        onClick = {
                            expanded = !expanded
                        },
                        modifier = Modifier,
                        expanded = expanded,
                        shape = MaterialShapes.Cookie6Sided.toShape(),
                        containerColor = colorScheme.primaryContainer,
                        contentColor = colorScheme.onPrimaryContainer,
                        text = { Text("Editar") }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(),
                contentPadding = PaddingValues(8.dp),
                floatingActionButtonPosition = FloatingToolbarHorizontalFabPosition.End,
                animationSpec = motionScheme.slowSpatialSpec(),
                content = {
                    IconButton(onClick = { onAction(BookAction.Add) }) {
                        Icon(Icons.Filled.Add, "Agregar")
                    }
                    IconButton(onClick = { /* Acción Favorito */ }) {
                        Icon(Icons.Filled.Favorite, "Favorito")
                    }
                    IconButton(onClick = { /* Acción Compartir */ }) {
                        Icon(Icons.Filled.Share, "Compartir")
                    }
                }
            )
        },
        content = { paddingValues ->
            BookContent(
                paddingValues = paddingValues,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    CenterAlignedTopAppBar(
        title = { Text("Mis Libros") },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun BookContent(paddingValues: PaddingValues) {
    LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(50) { index ->
            Text(
                text = "Libro #$index",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ParseTheme {
        BookScreen(
            state = BookState(),
            onAction = {}
        )
    }
}
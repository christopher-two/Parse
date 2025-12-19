package org.christophertwo.parse.feature.book.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.domain.models.chapter.Chapter
import org.christophertwo.parse.feature.book.presentation.components.scaffold.BookContent
import org.christophertwo.parse.feature.book.presentation.components.scaffold.BookTopBar

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
internal fun BookScreen(
    state: BookState,
    onAction: (BookAction) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val currentChapter = state.book?.chapters?.getOrNull(state.currentChapterIndex)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { BookTopBar(scrollBehavior, currentChapter ?: Chapter()) },
        content = { paddingValues ->
            BookContent(
                paddingValues = paddingValues,
                bookState = state,
                onAction = onAction
            )
        }
    )
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

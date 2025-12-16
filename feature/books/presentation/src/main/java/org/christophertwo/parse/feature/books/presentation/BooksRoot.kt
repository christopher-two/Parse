package org.christophertwo.parse.feature.books.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.domain.models.book.Book
import org.christophertwo.parse.feature.books.presentation.components.BooksList
import org.christophertwo.parse.feature.books.presentation.components.NoBook

@Composable
fun BooksRoot(
    viewModel: BooksViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BooksScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun BooksScreen(
    state: BooksState,
    onAction: (BooksAction) -> Unit,
) {
    state.books?.let { books ->
        BooksList(
            books = books,
            onClick = { book ->
                onAction(BooksAction.SelectBook(book))
            }
        )
    } ?: run {
        NoBook()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ParseTheme {
        BooksScreen(
            state = BooksState(
                books = listOf(
                    Book(
                        title = "A very long book title that should wrap to multiple lines",
                        author = "A very long author name",
                        year = 2024,
                        pages = 1234,
                        image = null
                    )
                )
            ),
            onAction = {}
        )
    }
}

package org.christophertwo.parse.feature.books.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.parse.core.ui.ParseTheme
import org.christophertwo.parse.feature.books.domain.Book

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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            state.books?.let { books ->
                BooksList(books)
            } ?: run {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    content = {
                        Text(
                            text = "No books found",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                        Button(
                            onClick = { onAction(BooksAction.AddBook) },
                        ) {
                            Text(
                                text = "Add book",
                                style = MaterialTheme.typography.labelLarge,
                            )
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun BooksList(
    books: List<Book>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(books) { book ->
                BookItem(book)
            }
        }
    )
}

@Composable
fun BookItem(x0: Book) {

}

@Preview
@Composable
private fun Preview() {
    ParseTheme {
        BooksScreen(
            state = BooksState(),
            onAction = {}
        )
    }
}
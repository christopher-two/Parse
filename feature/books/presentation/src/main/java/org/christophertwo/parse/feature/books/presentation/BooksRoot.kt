package org.christophertwo.parse.feature.books.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
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
                BooksList(
                    books = books,
                    onClick = { book ->
                        onAction(BooksAction.SelectBook(book))
                    }
                )
            } ?: run {
                NoBook(
                    onAction = onAction
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NoBook(
    modifier: Modifier = Modifier,
    onAction: (BooksAction) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Card(
                modifier = Modifier.size(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ),
                shape = MaterialShapes.Cookie9Sided.toShape(),
                onClick = {
                    onAction(BooksAction.AddBook)
                },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                    content = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Book",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }
    )

}

@Composable
fun BooksList(
    books: List<Book>,
    onClick: (Book) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(books) { book ->
                BookItem(
                    book = book,
                    onClick = {
                        onClick(book)
                    }
                )
            }
        }
    )
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
        onClick = onClick,
        content = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = book.image?.path,
                    contentDescription = "Book cover",
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.3f),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.7f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = book.year.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = book.pages.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = book.publisher,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
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
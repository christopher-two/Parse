package org.christophertwo.parse.feature.books.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.christophertwo.parse.domain.models.book.Book

@Composable
internal fun BooksList(
    books: List<Book>,
    onClick: (Book) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    ) {
        items(books) { book ->
            BookItem(
                book = book,
                onClick = {
                    onClick(book)
                }
            )
        }
    }
}
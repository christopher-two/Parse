package org.christophertwo.parse.feature.books.presentation

import androidx.core.net.toUri
import org.christophertwo.parse.core.common.model.Book

data class BooksState(
    val books: List<Book>? = listOf(
        Book(
            id = "1",
            title = "El Principito",
            author = "Antoine de Saint-Exupéry",
            year = 1943,
            pages = 96,
            image = "https://cdn.prod.website-files.com/6034d7d1f3e0f52c50b2adee/681b63dd7d9dbb4c4ce5ae76_WJlUnXLgNrZqh3HN_u7WMEnTVs1tV0qKwtUkvXJ2JTk.jpeg".toUri()
        )
    ),
    val selectedBook: Book? = null,
)
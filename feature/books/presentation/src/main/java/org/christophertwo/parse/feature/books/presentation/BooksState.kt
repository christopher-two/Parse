package org.christophertwo.parse.feature.books.presentation

import org.christophertwo.parse.feature.books.domain.Book

data class BooksState(
    val books: List<Book>? = null,
    val selectedBook: Book? = null,
)
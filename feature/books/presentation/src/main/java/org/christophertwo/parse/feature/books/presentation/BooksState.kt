package org.christophertwo.parse.feature.books.presentation

import org.christophertwo.parse.core.common.model.Book

data class BooksState(
    val books: List<Book>? = null,
    val selectedBook: Book? = null,
)
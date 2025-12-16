package org.christophertwo.parse.feature.book.presentation

import org.christophertwo.parse.core.common.model.Book

data class BookState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val error: String? = null
)
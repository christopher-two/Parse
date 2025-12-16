package org.christophertwo.parse.feature.book.presentation

import org.christophertwo.parse.domain.models.book.Book

data class BookState(
    val isLoading: Boolean = false,
    val book: Book? = null,
    val currentChapterIndex: Int = 0,
    val error: String? = null
)
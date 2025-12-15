package org.christophertwo.parse.feature.books.presentation

import org.christophertwo.parse.core.common.model.Book

sealed interface BooksAction {
    object AddBook : BooksAction
    data class RemoveBook(val book: Book) : BooksAction
    data class SelectBook(val book: Book) : BooksAction
}
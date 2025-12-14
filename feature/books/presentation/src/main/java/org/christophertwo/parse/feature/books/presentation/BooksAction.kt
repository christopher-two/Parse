package org.christophertwo.parse.feature.books.presentation

import org.christophertwo.parse.feature.books.domain.Book

sealed interface BooksAction {
    object AddBook : BooksAction
    data class RemoveBook(val book: Book) : BooksAction
}
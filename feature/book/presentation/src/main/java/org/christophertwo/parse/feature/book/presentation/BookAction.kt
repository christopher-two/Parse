package org.christophertwo.parse.feature.book.presentation

sealed interface BookAction {
    object Edit : BookAction
    object Add : BookAction
}
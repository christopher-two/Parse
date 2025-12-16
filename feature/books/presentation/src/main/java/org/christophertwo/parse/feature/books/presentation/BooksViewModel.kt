package org.christophertwo.parse.feature.books.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.christophertwo.parse.feature.navigation.navigator.GlobalNavigator

class BooksViewModel(
    private val globalNavigator: GlobalNavigator
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(BooksState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = BooksState()
        )

    fun onAction(action: BooksAction) {
        when (action) {
            BooksAction.AddBook -> {

            }

            is BooksAction.RemoveBook -> {

            }

            is BooksAction.SelectBook -> {
                globalNavigator.navigateBook(action.book.id)
            }
        }
    }
}
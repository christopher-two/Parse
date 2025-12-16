package org.christophertwo.parse.feature.book.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.christophertwo.parse.core.common.model.Book

class BookViewModel(
    private val id: String
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(BookState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                _state.update {
                    it.copy(
                        book = Book(
                            id = id,
                            title = "El Principito",
                            author = "Antoine de Saint-Exupéry",
                            year = 1943,
                            pages = 96,
                            image = null
                        )
                    )
                }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = BookState()
        )

    fun onAction(action: BookAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}
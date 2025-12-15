package org.christophertwo.parse.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
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
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.AddBook -> {
                _state.update {
                    it.copy(
                        showBottomSheet = true
                    )
                }
            }

            HomeAction.DismissBottomSheet -> {
                _state.update {
                    it.copy(
                        showBottomSheet = false,
                        bookDownloaded = false,
                        bookUri = "",
                        titleBook = "",
                        authorBook = "",
                        yearBook = "",
                        imageBook = null,
                        bookSelected = null
                    )
                }
            }

            is HomeAction.UpdateBookImage -> {
                _state.update {
                    it.copy(
                        imageBook = action.image
                    )
                }
            }

            is HomeAction.UpdateBookTitle -> {
                _state.update {
                    it.copy(
                        titleBook = action.title
                    )
                }
            }

            is HomeAction.BookUri -> {
                _state.update {
                    it.copy(
                        bookUri = action.uri,
                    )
                }
            }

            HomeAction.DownloadBook -> {
                // TODO: Implement book download logic
                _state.update {
                    it.copy(
                        bookDownloaded = true
                    )
                }
            }

            is HomeAction.UpdateBookAuthor -> {
                _state.update {
                    it.copy(
                        authorBook = action.author
                    )
                }
            }

            is HomeAction.UpdateBookYear -> {
                _state.update {
                    it.copy(
                        yearBook = action.year
                    )
                }
            }

            is HomeAction.BookSelected -> {
                // TODO: Implement book processing logic
                _state.update {
                    it.copy(
                        bookSelected = action.uri,
                        bookDownloaded = true
                    )
                }
            }

            HomeAction.SaveBook -> {
                // TODO: Implement book saving logic
            }
        }
    }
}
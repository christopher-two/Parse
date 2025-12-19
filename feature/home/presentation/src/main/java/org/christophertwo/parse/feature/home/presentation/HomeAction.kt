package org.christophertwo.parse.feature.home.presentation

import android.net.Uri

sealed interface HomeAction {
    object AddBook : HomeAction
    object DismissBottomSheet : HomeAction
    data class UpdateBookTitle(val title: String) : HomeAction
    data class UpdateBookImage(val image: Uri) : HomeAction
    data class BookSelected(val uri: Uri) : HomeAction
    object SaveBook : HomeAction
}
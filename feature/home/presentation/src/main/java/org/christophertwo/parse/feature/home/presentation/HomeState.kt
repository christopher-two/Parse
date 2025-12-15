package org.christophertwo.parse.feature.home.presentation

import android.net.Uri

data class HomeState(
    val showBottomSheet: Boolean = false,
    val titleBook: String = "",
    val authorBook: String = "",
    val yearBook: String = "",
    val imageBook: Uri? = null,
    val pagesBook: Int = 0,
    val bookUri: String = "",
    val bookDownloaded: Boolean = false,
    val isSaving: Boolean = false,
    val bookSelected: Uri? = null,
)
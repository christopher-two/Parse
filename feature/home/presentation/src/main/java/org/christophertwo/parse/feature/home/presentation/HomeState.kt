package org.christophertwo.parse.feature.home.presentation

import android.net.Uri

data class HomeState(
    val showBottomSheet: Boolean = false,
    val titleBook: String = "",
    val imageBook: Uri? = null,
    val pagesBook: Int = 0,
    val bookDownloaded: Boolean = false,
    val isSaving: Boolean = false,
    val bookSelected: Uri? = null,
    // Import PDF
    val isImportingPdf: Boolean = false,
    val importedPdfPath: String? = null,
    val importPdfError: String? = null,
)
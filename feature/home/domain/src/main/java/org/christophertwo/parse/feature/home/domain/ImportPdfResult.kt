package org.christophertwo.parse.feature.home.domain

sealed interface ImportPdfResult {
    data class Success(val pdf: ImportedPdf) : ImportPdfResult
    data class Error(val error: ImportPdfError) : ImportPdfResult
}

sealed interface ImportPdfError {
    data object InvalidUri : ImportPdfError
    data object NotPdf : ImportPdfError
    data object OpenFailed : ImportPdfError
    data object CopyFailed : ImportPdfError
}


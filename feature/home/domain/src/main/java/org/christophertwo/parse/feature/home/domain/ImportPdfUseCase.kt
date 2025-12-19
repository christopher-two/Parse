package org.christophertwo.parse.feature.home.domain

class ImportPdfUseCase(
    private val repository: HomeRepository,
) {
    suspend operator fun invoke(contentUriString: String): ImportPdfResult {
        if (contentUriString.isBlank()) {
            return ImportPdfResult.Error(ImportPdfError.InvalidUri)
        }
        return repository.importPdfFromContentUri(contentUriString)
    }
}


package org.christophertwo.parse.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.parse.feature.home.domain.ImportPdfError
import org.christophertwo.parse.feature.home.domain.ImportPdfResult
import org.christophertwo.parse.feature.home.domain.ImportPdfUseCase

class HomeViewModel(
    private val importPdfUseCase: ImportPdfUseCase,
) : ViewModel() {

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
                        titleBook = "",
                        imageBook = null,
                        bookSelected = null,
                        isImportingPdf = false,
                        importedPdfPath = null,
                        importPdfError = null,
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


            is HomeAction.BookSelected -> {
                // Importar PDF: copiar a almacenamiento privado
                _state.update {
                    it.copy(
                        bookSelected = action.uri,
                        isImportingPdf = true,
                        importPdfError = null,
                        importedPdfPath = null,
                    )
                }

                viewModelScope.launch {
                    when (val result = importPdfUseCase(action.uri.toString())) {
                        is ImportPdfResult.Success -> {
                            _state.update {
                                it.copy(
                                    isImportingPdf = false,
                                    importedPdfPath = result.pdf.absolutePath,
                                    bookDownloaded = true,
                                )
                            }
                        }

                        is ImportPdfResult.Error -> {
                            _state.update {
                                it.copy(
                                    isImportingPdf = false,
                                    importPdfError = result.error.toUiMessage(),
                                )
                            }
                        }
                    }
                }
            }

            HomeAction.SaveBook -> {
                // TODO: Implement book saving logic
            }
        }
    }

    private fun ImportPdfError.toUiMessage(): String = when (this) {
        ImportPdfError.InvalidUri -> "URI inválida"
        ImportPdfError.NotPdf -> "El archivo seleccionado no es un PDF"
        ImportPdfError.OpenFailed -> "No se pudo abrir el archivo"
        ImportPdfError.CopyFailed -> "No se pudo copiar el archivo a almacenamiento privado"
    }
}
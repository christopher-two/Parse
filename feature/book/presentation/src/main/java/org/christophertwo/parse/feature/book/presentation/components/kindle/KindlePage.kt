package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * Muestra el contenido de una única página, incluyendo el texto principal
 * y el número de página.
 *
 * @param text El texto de la página a mostrar.
 * @param pageNumber El número de la página actual.
 * @param totalPages El número total de páginas.
 * @param textStyle El estilo a aplicar al texto principal.
 * @param contentPadding El padding a aplicar alrededor del contenido.
 * @param modifier Modificador para el contenedor de la página.
 */
@Composable
internal fun KindlePage(
    text: String,
    pageNumber: Int,
    totalPages: Int,
    textStyle: TextStyle,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.fillMaxSize()
        )
    }
}
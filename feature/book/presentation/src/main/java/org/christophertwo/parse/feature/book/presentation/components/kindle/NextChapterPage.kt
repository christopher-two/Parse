package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.christophertwo.parse.core.ui.ParseTheme

/**
 * Página que se muestra al final de un capítulo para navegar al siguiente.
 *
 * @param nextChapterTitle El título del siguiente capítulo. Puede ser nulo.
 * @param onNavigateToNextChapter La acción a ejecutar para navegar al siguiente capítulo.
 * @param modifier Modificador para el contenedor de la página.
 */
@Composable
internal fun NextChapterPage(
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .then(modifier)
    ) {
        Text("You completed the chapter", style = MaterialTheme.typography.titleLarge)
        nextChapterTitle?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Icon(Icons.Default.ArrowForward, contentDescription = "Next Chapter")
    }
}

@Composable
@Preview
private fun Preview() {
    ParseTheme {
        NextChapterPage(
            nextChapterTitle = "Capítulo 2",
            onNavigateToNextChapter = {}
        )
    }
}
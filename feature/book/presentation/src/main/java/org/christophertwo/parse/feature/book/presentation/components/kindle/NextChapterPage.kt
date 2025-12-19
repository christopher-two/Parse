package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.christophertwo.parse.core.ui.ParseTheme

/**
 * Página que se muestra al final de un capítulo para navegar al siguiente.
 *
 * @param nextChapterTitle El título del siguiente capítulo. Puede ser nulo.
 * @param onNavigateToNextChapter La acción a ejecutar para navegar al siguiente capítulo.
 * @param modifier Modificador para el contenedor de la página.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun NextChapterPage(
    actualChapterTitle: String,
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "You completed the chapter \n${actualChapterTitle}.",
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif
            ),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        nextChapterTitle?.let {
            Text(
                text = "Next Chapter: $it",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        FilledTonalIconButton(
            onClick = onNavigateToNextChapter,
            shape = MaterialShapes.Burst.toShape(),
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            modifier = Modifier.size(80.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Next Chapter",
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun Preview() {
    ParseTheme(
        isDark = true
    ) {
        NextChapterPage(
            nextChapterTitle = "Capítulo 2",
            actualChapterTitle = "Capítulo 1",
            onNavigateToNextChapter = {}
        )
    }
}
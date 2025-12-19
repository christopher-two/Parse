package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Componente estilo Kindle que recibe un texto largo y lo pagina
 * dinámicamente según el tamaño disponible en pantalla.
 * Este componente gestiona el cálculo de las páginas en segundo plano
 * y muestra un indicador de carga mientras se procesa el texto.
 *
 * @param paddingValues Padding proveniente del Scaffold, para ajustar el contenido.
 * @param fullChapterText El texto completo del capítulo a mostrar.
 * @param onTapPage Lambda que se invoca al hacer tap en una página.
 * @param nextChapterTitle Título del siguiente capítulo, para mostrarlo en la última página.
 * @param onNavigateToNextChapter Lambda para navegar al siguiente capítulo.
 * @param contentPadding Padding interno para el contenido del texto en cada página.
 * @param textColor Color del texto.
 * @param backgroundColor Color de fondo del "papel".
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
internal fun KindlePaperView(
    paddingValues: PaddingValues,
    fullChapterText: String,
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    currentChapterTitle: String
) {
    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        fontFamily = FontFamily.Serif,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.Justify,
        color = textColor
    )

    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current

    var pages by remember { mutableStateOf<List<String>>(emptyList()) }
    var isCalculating by remember { mutableStateOf(true) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(paddingValues)
    ) {
        val boxWidth = maxWidth
        val boxHeight = maxHeight

        val horizontalPaddingPx = with(density) {
            contentPadding.calculateLeftPadding(LayoutDirection.Ltr)
                .toPx() +
                    contentPadding.calculateRightPadding(LayoutDirection.Ltr)
                        .toPx()
        }
        val verticalPaddingPx = with(density) {
            contentPadding.calculateTopPadding().toPx() +
                    contentPadding.calculateBottomPadding().toPx()
        }

        LaunchedEffect(fullChapterText, boxWidth, boxHeight, textStyle) {
            isCalculating = true
            val calculatedPages = withContext(Dispatchers.Default) {
                splitTextToPages(
                    text = fullChapterText,
                    measurer = textMeasurer,
                    style = textStyle,
                    widthPx = constraints.maxWidth - horizontalPaddingPx,
                    heightPx = constraints.maxHeight - verticalPaddingPx,
                    density = density
                )
            }
            pages = calculatedPages
            isCalculating = false
        }

        if (isCalculating || pages.isEmpty()) {
            KindleLoadingView()
        } else {
            KindlePagerContent(
                pages = pages,
                textStyle = textStyle,
                contentPadding = contentPadding,
                nextChapterTitle = nextChapterTitle,
                onNavigateToNextChapter = onNavigateToNextChapter,
                currentChapterTitle = currentChapterTitle
            )
        }
    }
}

/**
 * Muestra un indicador de carga centrado en la pantalla.
 *
 * @param modifier Modificador para personalizar el contenedor del indicador.
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun KindleLoadingView(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ContainedLoadingIndicator()
    }
}
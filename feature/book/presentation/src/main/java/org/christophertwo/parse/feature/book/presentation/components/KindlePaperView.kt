package org.christophertwo.parse.feature.book.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
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
    onTapPage: () -> Unit,
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = MaterialTheme.colorScheme.surface
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
            contentPadding.calculateLeftPadding(androidx.compose.ui.unit.LayoutDirection.Ltr)
                .toPx() +
                    contentPadding.calculateRightPadding(androidx.compose.ui.unit.LayoutDirection.Ltr)
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
                onTapPage = onTapPage,
                nextChapterTitle = nextChapterTitle,
                onNavigateToNextChapter = onNavigateToNextChapter
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

/**
 * Muestra el contenido de una única página, incluyendo el texto principal
 * y el número de página.
 *
 * @param text El texto de la página a mostrar.
 * @param pageNumber El número de la página actual.
 * @param totalPages El número total de páginas.
 * @param textStyle El estilo a aplicar al texto principal.
 * @param contentPadding El padding a aplicar alrededor del contenido.
 * @param onTapPage La acción a ejecutar cuando se hace tap en la página.
 * @param modifier Modificador para el contenedor de la página.
 */
@Composable
private fun KindlePage(
    text: String,
    pageNumber: Int,
    totalPages: Int,
    textStyle: TextStyle,
    contentPadding: PaddingValues,
    onTapPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onTapPage
            )
            .padding(contentPadding),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = "$pageNumber / $totalPages",
            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 16.dp)
        )
    }
}

/**
 * Contenedor que gestiona el HorizontalPager para mostrar las páginas del libro.
 *
 * @param pages La lista de strings, donde cada string es una página.
 * @param textStyle El estilo a aplicar al texto de las páginas.
 * @param contentPadding El padding a aplicar al contenido de cada página.
 * @param onTapPage La acción a ejecutar al hacer tap en una página.
 * @param nextChapterTitle Título del siguiente capítulo, para mostrarlo en la última página.
 * @param onNavigateToNextChapter Lambda para navegar al siguiente capítulo.
 * @param modifier Modificador para el HorizontalPager.
 */
@Composable
private fun KindlePagerContent(
    pages: List<String>,
    textStyle: TextStyle,
    contentPadding: PaddingValues,
    onTapPage: () -> Unit,
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size + 1 })

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { pageIndex ->
        if (pageIndex < pages.size) {
            KindlePage(
                text = pages[pageIndex],
                pageNumber = pageIndex + 1,
                totalPages = pages.size,
                textStyle = textStyle,
                contentPadding = contentPadding,
                onTapPage = onTapPage
            )
        } else {
            NextChapterPage(
                nextChapterTitle = nextChapterTitle,
                onNavigateToNextChapter = onNavigateToNextChapter
            )
        }
    }
}

/**
 * Página que se muestra al final de un capítulo para navegar al siguiente.
 *
 * @param nextChapterTitle El título del siguiente capítulo. Puede ser nulo.
 * @param onNavigateToNextChapter La acción a ejecutar para navegar al siguiente capítulo.
 * @param modifier Modificador para el contenedor de la página.
 */
@Composable
private fun NextChapterPage(
    nextChapterTitle: String?,
    onNavigateToNextChapter: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateToNextChapter),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Siguiente Capítulo", style = MaterialTheme.typography.titleLarge)
            nextChapterTitle?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Siguiente Capítulo")
        }
    }
}

package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

/**
 * Contenedor que gestiona el HorizontalPager para mostrar las páginas del libro.
 *
 * @param pages La lista de strings, donde cada string es una página.
 * @param textStyle El estilo a aplicar al texto de las páginas.
 * @param contentPadding El padding a aplicar al contenido de cada página.
 * @param nextChapterTitle Título del siguiente capítulo, para mostrarlo en la última página.
 * @param onNavigateToNextChapter Lambda para navegar al siguiente capítulo.
 * @param modifier Modificador para el HorizontalPager.
 */
@Composable
internal fun KindlePagerContent(
    pages: List<String>,
    textStyle: TextStyle,
    contentPadding: PaddingValues,
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
                contentPadding = contentPadding
            )
        } else {
            NextChapterPage(
                nextChapterTitle = nextChapterTitle,
                onNavigateToNextChapter = onNavigateToNextChapter
            )
        }
    }
}
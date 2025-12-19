package org.christophertwo.parse.feature.book.presentation.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.christophertwo.parse.feature.book.presentation.BookAction
import org.christophertwo.parse.feature.book.presentation.BookState
import org.christophertwo.parse.feature.book.presentation.components.kindle.KindlePaperView

@Composable
internal fun BookContent(
    paddingValues: PaddingValues,
    bookState: BookState,
    onAction: (BookAction) -> Unit
) {
    val currentChapter = bookState.book?.chapters?.getOrNull(bookState.currentChapterIndex)
    val nextChapter = bookState.book?.chapters?.getOrNull(bookState.currentChapterIndex + 1)

    KindlePaperView(
        fullChapterText = currentChapter?.content ?: "",
        paddingValues = paddingValues,
        contentPadding = PaddingValues(
            bottom = 60.dp,
            start = 24.dp,
            end = 24.dp
        ),
        nextChapterTitle = nextChapter?.title,
        onNavigateToNextChapter = {
            onAction(BookAction.NavigateToNextChapter)
        },
    )
}
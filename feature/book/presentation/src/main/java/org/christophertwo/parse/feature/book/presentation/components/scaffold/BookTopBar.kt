package org.christophertwo.parse.feature.book.presentation.components.scaffold

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import org.christophertwo.parse.domain.models.chapter.Chapter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    chapter: Chapter
) {
    CenterAlignedTopAppBar(
        title = { Text(chapter.title) },
        scrollBehavior = scrollBehavior
    )
}


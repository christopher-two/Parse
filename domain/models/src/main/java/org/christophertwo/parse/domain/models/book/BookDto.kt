package org.christophertwo.parse.domain.models.book

import kotlinx.serialization.Serializable
import org.christophertwo.parse.domain.models.chapter.ChapterDto
import java.util.UUID

@Serializable
data class BookDto(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val chapters: List<ChapterDto>,
    val image: String?
)

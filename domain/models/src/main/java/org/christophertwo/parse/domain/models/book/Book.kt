package org.christophertwo.parse.domain.models.book

import android.net.Uri
import org.christophertwo.parse.domain.models.chapter.Chapter
import java.util.UUID

data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val chapters: List<Chapter>? = null,
    val image: Uri?
) {
    fun toBookDto(): BookDto = BookDto(
        id = id,
        title = title,
        author = author,
        year = year,
        pages = pages,
        chapters = chapters?.map { it.toChapterDto() } ?: emptyList(),
        image = image?.toString()
    )
}
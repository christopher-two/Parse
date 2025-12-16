package org.christophertwo.parse.domain.models.chapter

import java.util.UUID

data class Chapter(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val content: String = "",
) {
    fun toChapterDto(): ChapterDto = ChapterDto(
        id = id,
        title = title,
        content = content,
    )
}
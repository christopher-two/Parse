package org.christophertwo.parse.domain.models.chapter

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ChapterDto(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String
)

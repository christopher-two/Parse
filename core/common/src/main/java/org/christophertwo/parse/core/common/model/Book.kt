package org.christophertwo.parse.core.common.model

import android.net.Uri
import java.util.UUID

data class Book(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val image: Uri?
)
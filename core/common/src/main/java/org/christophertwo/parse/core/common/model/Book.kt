package org.christophertwo.parse.core.common.model

import android.net.Uri

data class Book(
    val title: String,
    val author: String,
    val year: Int,
    val pages: Int,
    val image: Uri?
)
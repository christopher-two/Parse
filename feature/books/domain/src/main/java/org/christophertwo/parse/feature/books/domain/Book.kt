package org.christophertwo.parse.feature.books.domain

import android.net.Uri

data class Book(
    val title: String,
    val author: String,
    val year: Int,
    val publisher: String,
    val pages: Int,
    val image: Uri?
)
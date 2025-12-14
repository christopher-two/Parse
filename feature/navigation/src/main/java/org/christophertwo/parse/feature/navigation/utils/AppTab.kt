package org.christophertwo.parse.feature.navigation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(
    val label: String,
    val icon: ImageVector
) {
    Books("Libros", Icons.AutoMirrored.Filled.MenuBook)
}
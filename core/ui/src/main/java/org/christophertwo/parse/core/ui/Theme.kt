package org.christophertwo.parse.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ParseTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    seedColor: Color = Color(0xFF009688),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = seedColor,
        isDark = isDark,
        style = PaletteStyle.Expressive,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        animationSpec = MaterialTheme.motionScheme.slowSpatialSpec(),
        animate = true,
        shapes = MaterialTheme.shapes,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    )
}
package org.christophertwo.parse.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import org.christophertwo.parse.domain.models.settings.Contrast as AppContrast

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ParseTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    isAmoled: Boolean = false,
    seedColor: Color = Color(0xFF009688),
    contrast: AppContrast = AppContrast.MEDIUM,
    fontSize: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val kolorContrast = when (contrast) {
        AppContrast.LOW -> com.materialkolor.Contrast.Reduced
        AppContrast.MEDIUM -> com.materialkolor.Contrast.Default
        AppContrast.HIGH -> com.materialkolor.Contrast.High
    }

    val defaultTypography = MaterialTheme.typography
    val scaledTypography = Typography(
        displayLarge = defaultTypography.displayLarge.scale(fontSize),
        displayMedium = defaultTypography.displayMedium.scale(fontSize),
        displaySmall = defaultTypography.displaySmall.scale(fontSize),
        headlineLarge = defaultTypography.headlineLarge.scale(fontSize),
        headlineMedium = defaultTypography.headlineMedium.scale(fontSize),
        headlineSmall = defaultTypography.headlineSmall.scale(fontSize),
        titleLarge = defaultTypography.titleLarge.scale(fontSize),
        titleMedium = defaultTypography.titleMedium.scale(fontSize),
        titleSmall = defaultTypography.titleSmall.scale(fontSize),
        bodyLarge = defaultTypography.bodyLarge.scale(fontSize),
        bodyMedium = defaultTypography.bodyMedium.scale(fontSize),
        bodySmall = defaultTypography.bodySmall.scale(fontSize),
        labelLarge = defaultTypography.labelLarge.scale(fontSize),
        labelMedium = defaultTypography.labelMedium.scale(fontSize),
        labelSmall = defaultTypography.labelSmall.scale(fontSize)
    )

    DynamicMaterialTheme(
        seedColor = seedColor,
        isDark = isDark,
        style = PaletteStyle.Expressive,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        animate = true,
        shapes = MaterialTheme.shapes,
        typography = scaledTypography,
        contrastLevel = kolorContrast.value,
        isAmoled = isAmoled,
        content = {
            Surface(
                color = colorScheme.background,
                contentColor = colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    )
}

private fun TextStyle.scale(factor: Float): TextStyle {
    return copy(
        fontSize = fontSize * factor,
        lineHeight = lineHeight * factor
    )
}

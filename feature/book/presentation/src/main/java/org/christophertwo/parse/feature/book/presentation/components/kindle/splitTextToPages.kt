package org.christophertwo.parse.feature.book.presentation.components.kindle

import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import kotlin.math.floor

/**
 * Función pura que toma un texto largo y lo divide en una lista de páginas
 * que caben perfectamente en el tamaño especificado.
 */
internal fun splitTextToPages(
    text: String,
    measurer: TextMeasurer,
    style: TextStyle,
    widthPx: Float,
    heightPx: Float,
    density: Density
): List<String> {
    val pages = mutableListOf<String>()
    var remainingText = text

    // Creamos las restricciones de tamaño para el medidor
    // width: fijo al ancho de la página (menos padding)
    // height: infinito (para medir cuánto ocuparía todo el texto)
    val constraints = Constraints(
        maxWidth = floor(widthPx).toInt(),
        maxHeight = Constraints.Infinity
    )

    while (remainingText.isNotEmpty()) {
        // 1. Medimos el texto restante
        val result = measurer.measure(
            text = remainingText,
            style = style,
            constraints = constraints
        )

        // 2. Verificamos si todo el texto restante cabe en una sola página
        if (result.size.height <= heightPx) {
            pages.add(remainingText)
            break
        }

        // 3. Si no cabe, buscamos dónde cortar.
        // Recorremos las líneas detectadas por el TextMeasurer
        var lastVisibleCharIndex = 0
        val lineCount = result.lineCount
        
        for (i in 0 until lineCount) {
            // Obtenemos la posición inferior de la línea actual
            val lineBottom = result.getLineBottom(i)
            
            // Si la línea se sale del alto disponible, cortamos en la línea anterior
            if (lineBottom > heightPx) {
                // El corte debe ser al final de la línea anterior (i - 1)
                // Si i es 0, significa que ni una línea cabe (caso raro, fuente gigante),
                // forzamos cortar en la primera línea.
                val cutLineIndex = if (i > 0) i - 1 else 0
                lastVisibleCharIndex = result.getLineEnd(cutLineIndex, visibleEnd = true)
                break
            }
            
            // Si llegamos a la última línea y aún cabe, el índice es el final
            if (i == lineCount - 1) {
                lastVisibleCharIndex = result.getLineEnd(i, visibleEnd = true)
            }
        }

        // 4. Cortamos el string y lo agregamos a las páginas
        // Aseguramos que el índice no sea 0 para evitar bucles infinitos si algo falla
        if (lastVisibleCharIndex == 0) lastVisibleCharIndex = remainingText.length

        val pageContent = remainingText.take(lastVisibleCharIndex)
        pages.add(pageContent)

        // 5. Preparamos el texto restante para la siguiente iteración
        // Eliminamos espacios en blanco al inicio de la nueva página para que quede limpio
        remainingText = remainingText.substring(lastVisibleCharIndex).trimStart()
    }

    return pages
}
package org.christophertwo.parse.feature.home.domain

/**
 * Representa un PDF ya copiado a almacenamiento privado.
 */
data class ImportedPdf(
    val displayName: String,
    /** Path absoluto dentro del almacenamiento privado (p.ej. /data/user/0/.../files/...). */
    val absolutePath: String,
    val bytes: Long,
)


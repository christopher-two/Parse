package org.christophertwo.parse.feature.home.domain

/**
 * Contrato de Home para lógica relacionada a importación de PDFs.
 *
 * Nota: el dominio no depende de clases Android, por eso trabajamos con Strings.
 */
interface HomeRepository {
    /**
     * Copia un PDF seleccionado por el usuario (content://) a almacenamiento privado de la app.
     * @param contentUriString Uri del picker (`content://...`) como String.
     */
    suspend fun importPdfFromContentUri(contentUriString: String): ImportPdfResult
}


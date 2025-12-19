package org.christophertwo.parse.feature.home.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import org.christophertwo.parse.feature.home.domain.HomeRepository
import org.christophertwo.parse.feature.home.domain.ImportPdfError
import org.christophertwo.parse.feature.home.domain.ImportPdfResult
import org.christophertwo.parse.feature.home.domain.ImportedPdf
import java.io.File
import java.io.FileOutputStream

class HomeRepositoryImpl(
    private val appContext: Context,
    private val contentResolver: ContentResolver = appContext.contentResolver,
) : HomeRepository {

    override suspend fun importPdfFromContentUri(contentUriString: String): ImportPdfResult {
        val uri = runCatching { contentUriString.toUri() }.getOrNull()
            ?: return ImportPdfResult.Error(ImportPdfError.InvalidUri)

        // Validación rápida por MIME (si el provider lo reporta)
        val mime = runCatching { contentResolver.getType(uri) }.getOrNull()
        if (mime != null && mime != "application/pdf") {
            return ImportPdfResult.Error(ImportPdfError.NotPdf)
        }

        val meta = queryMeta(uri)
        val displayName = sanitizeFileName(
            meta.displayName ?: "import_${System.currentTimeMillis()}.pdf"
        ).ensurePdfExtension()

        val dir = File(appContext.filesDir, "imports/pdfs").apply { mkdirs() }
        val targetFile = uniqueFile(dir, displayName)

        val inputStream = runCatching { contentResolver.openInputStream(uri) }.getOrNull()
            ?: return ImportPdfResult.Error(ImportPdfError.OpenFailed)

        val bytesCopied = try {
            inputStream.use { input ->
                FileOutputStream(targetFile).use { output ->
                    input.copyTo(output)
                }
            }
            targetFile.length()
        } catch (_: Throwable) {
            runCatching { targetFile.delete() }
            return ImportPdfResult.Error(ImportPdfError.CopyFailed)
        }

        // Fallback adicional: si MIME era null, validamos firma PDF.
        if (mime == null && !targetFile.looksLikePdf()) {
            runCatching { targetFile.delete() }
            return ImportPdfResult.Error(ImportPdfError.NotPdf)
        }

        return ImportPdfResult.Success(
            ImportedPdf(
                displayName = targetFile.name,
                absolutePath = targetFile.absolutePath,
                bytes = bytesCopied,
            )
        )
    }

    private data class Meta(
        val displayName: String?,
    )

    private fun queryMeta(uri: Uri): Meta {
        var cursor: Cursor? = null
        return try {
            cursor = contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                Meta(displayName = if (nameIndex >= 0) cursor.getString(nameIndex) else null)
            } else {
                Meta(displayName = null)
            }
        } catch (_: Throwable) {
            Meta(displayName = null)
        } finally {
            runCatching { cursor?.close() }
        }
    }

    private fun uniqueFile(dir: File, displayName: String): File {
        var candidate = File(dir, displayName)
        if (!candidate.exists()) return candidate

        val base = candidate.nameWithoutExtension
        val ext = candidate.extension
        var i = 1
        while (candidate.exists()) {
            candidate = File(dir, "$base ($i).$ext")
            i++
        }
        return candidate
    }

    private fun sanitizeFileName(name: String): String =
        name.replace(Regex("[\\\\/:*?\"<>|]"), "_").trim().ifBlank { "file.pdf" }

    private fun String.ensurePdfExtension(): String =
        if (lowercase().endsWith(".pdf")) this else "$this.pdf"

    private fun File.looksLikePdf(): Boolean {
        if (!exists() || length() < 4) return false
        return runCatching {
            inputStream().use { input ->
                val header = ByteArray(4)
                val read = input.read(header)
                read == 4 && header.contentEquals(byteArrayOf('%'.code.toByte(), 'P'.code.toByte(), 'D'.code.toByte(), 'F'.code.toByte()))
            }
        }.getOrDefault(false)
    }
}


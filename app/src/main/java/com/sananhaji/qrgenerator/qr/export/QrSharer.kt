package com.sananhaji.qrgenerator.qr.export

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.sananhaji.qrgenerator.utils.fileProviderAuthority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/** Shares a rendered code through the system share sheet via the app's FileProvider. */
class QrSharer(private val context: Context) {

    suspend fun share(
        bitmap: Bitmap,
        chooserTitle: String,
    ) {
        val file = withContext(Dispatchers.IO) { writeToCache(bitmap) }
        val uri = FileProvider.getUriForFile(context, context.fileProviderAuthority, file)
        val send = Intent(Intent.ACTION_SEND).apply {
            type = PNG_MIME_TYPE
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(send, chooserTitle))
    }

    private fun writeToCache(bitmap: Bitmap): File {
        val directory = File(context.cacheDir, SHARE_DIRECTORY).apply { mkdirs() }
        return File(directory, SHARE_FILE_NAME).apply {
            outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, it) }
        }
    }

    private companion object {

        const val SHARE_DIRECTORY = "shared"

        const val SHARE_FILE_NAME = "qr.png"

        const val PNG_MIME_TYPE = "image/png"

        const val PNG_QUALITY = 100
    }
}

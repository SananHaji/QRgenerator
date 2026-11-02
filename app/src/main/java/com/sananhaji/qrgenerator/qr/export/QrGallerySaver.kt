package com.sananhaji.qrgenerator.qr.export

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Writes PNGs into `Pictures/QRCodeGenerator` through MediaStore. */
class QrGallerySaver(context: Context) {

    private val resolver = context.applicationContext.contentResolver

    suspend fun save(
        bitmap: Bitmap,
        name: String,
    ): Boolean = withContext(Dispatchers.IO) {
        runCatching { write(bitmap, name) }
            .onFailure { Log.e(TAG, "Saving $name failed", it) }
            .getOrDefault(false)
    }

    private fun write(
        bitmap: Bitmap,
        name: String,
    ): Boolean {
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues(name)) ?: return false
        return resolver.openOutputStream(uri)
            ?.use { bitmap.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY, it) } == true
    }

    private fun contentValues(name: String) = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "$name$PNG_EXTENSION")
        put(MediaStore.MediaColumns.MIME_TYPE, PNG_MIME_TYPE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/$ALBUM_NAME")
        }
    }

    private companion object {

        const val TAG = "QrGallerySaver"

        const val ALBUM_NAME = "QRCodeGenerator"

        const val PNG_EXTENSION = ".png"

        const val PNG_MIME_TYPE = "image/png"

        const val PNG_QUALITY = 100
    }
}

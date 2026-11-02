package com.sananhaji.qrgenerator.qr.render

import android.graphics.Bitmap
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.google.zxing.WriterException
import com.sananhaji.qrgenerator.qr.image.QrImageLoader
import com.sananhaji.qrgenerator.qr.model.QrStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QrRenderer(private val imageLoader: QrImageLoader) {

    /**
     * Content is encoded as UTF-8 so non-Latin text (for example Azerbaijani letters)
     * survives; ZXing would otherwise fall back to ISO-8859-1 and replace them with `?`.
     *
     * @return `null` when the content does not fit into the largest QR version.
     */
    suspend fun render(
        content: String,
        style: QrStyle,
        sizePx: Int,
    ): Bitmap? {
        val logo = style.logo?.let { imageLoader.load(it) }
        val background = style.backgroundImage?.let { imageLoader.load(it) }
        return withContext(Dispatchers.Default) {
            val options = style.toVectorOptions(
                logoDrawable = logo,
                backgroundDrawable = background,
            )
            try {
                QrCodeDrawable(QrData.Text(content), options, Charsets.UTF_8).toBitmap(sizePx, sizePx)
            } catch (e: WriterException) {
                Log.w(TAG, "Content does not fit into a QR code", e)
                null
            }
        }
    }

    private companion object {

        const val TAG = "QrRenderer"
    }
}

package com.sananhaji.qrgenerator.qr.image

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.github.alexzhirkevich.customqrgenerator.style.BitmapScale

/**
 * Where a logo or background image of a QR code comes from.
 *
 * Each source carries its own [scale]: bundled assets are drawn to fill their slot exactly,
 * the way the company design was made, while pictures picked by the user keep their aspect ratio.
 */
sealed interface QrImageSource {

    val scale: BitmapScale

    fun load(context: Context): Drawable?

    data class Resource(@DrawableRes val resId: Int) : QrImageSource {

        override val scale: BitmapScale get() = BitmapScale.FitXY

        override fun load(context: Context): Drawable? = ContextCompat.getDrawable(context, resId)
    }

    data class Picked(val uri: Uri) : QrImageSource {

        override val scale: BitmapScale get() = BitmapScale.CenterCrop

        override fun load(context: Context): Drawable? =
            SampledBitmapDecoder.decode(context.contentResolver, uri)
                ?.let { BitmapDrawable(context.resources, it) }
    }
}

package com.sananhaji.qrgenerator.qr.image

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

/**
 * Decodes a picked picture at a reduced size, so a 12 MP camera photo
 * used as a logo or background does not exhaust memory.
 */
internal object SampledBitmapDecoder {

    private const val MAX_DIMENSION_PX = 1024

    private const val SAMPLE_STEP = 2

    fun decode(resolver: ContentResolver, uri: Uri): Bitmap? {
        val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, bounds) }

        val options = BitmapFactory.Options().apply {
            inSampleSize = sampleSize(
                width = bounds.outWidth,
                height = bounds.outHeight,
            )
        }
        return resolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it, null, options) }
    }

    private fun sampleSize(
        width: Int,
        height: Int,
    ): Int {
        val largest = maxOf(width, height)
        var sample = 1
        while (largest / (sample * SAMPLE_STEP) >= MAX_DIMENSION_PX) {
            sample *= SAMPLE_STEP
        }
        return sample
    }
}

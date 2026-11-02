package com.sananhaji.qrgenerator.qr.image

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Loads [QrImageSource]s off the main thread and keeps the last few decoded,
 * so moving a slider in the editor does not decode the same picture on every frame.
 */
class QrImageLoader(context: Context) {

    private val appContext = context.applicationContext

    private val cache = LruCache<QrImageSource, Drawable.ConstantState>(CACHE_SIZE)

    suspend fun load(source: QrImageSource): Drawable? = withContext(Dispatchers.IO) {
        cachedOrLoaded(source)?.newDrawable(appContext.resources)
    }

    private fun cachedOrLoaded(source: QrImageSource): Drawable.ConstantState? =
        cache.get(source)
            ?: source.load(appContext)?.constantState?.also { cache.put(source, it) }

    private companion object {

        const val CACHE_SIZE = 4
    }
}

package com.sananhaji.qrgenerator.qr.render

import android.graphics.drawable.Drawable
import com.github.alexzhirkevich.customqrgenerator.HighlightingType
import com.github.alexzhirkevich.customqrgenerator.QrHighlighting
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import com.sananhaji.qrgenerator.qr.model.QrStyle

/** Gap between the logo and the surrounding pixels, as in the original company design. */
private const val LOGO_PADDING = .2f

/**
 * Maps the app's [QrStyle] onto the library options. Error correction stays on the
 * library's `Auto`, which raises the level to fit the logo size.
 */
internal fun QrStyle.toVectorOptions(
    logoDrawable: Drawable?,
    backgroundDrawable: Drawable?,
): QrVectorOptions = QrVectorOptions.Builder()
    .setPadding(margin)
    .setColors(vectorColors())
    .setShapes(vectorShapes())
    .setLogo(vectorLogo(logoDrawable))
    .setBackground(vectorBackground(backgroundDrawable))
    .setAnchorsHighlighting(highlighting())
    .build()

private fun QrStyle.vectorColors(): QrVectorColors = QrVectorColors(
    dark = QrVectorColor.Solid(darkColor),
    ball = QrVectorColor.Solid(ballColor),
    frame = QrVectorColor.Solid(frameColor),
)

private fun QrStyle.vectorShapes(): QrVectorShapes = QrVectorShapes(
    darkPixel = pixelShape.shape,
    ball = ballShape.shape,
    frame = frameShape.shape,
)

private fun QrStyle.vectorLogo(drawable: Drawable?): QrVectorLogo {
    if (drawable == null) return QrVectorLogo()
    return QrVectorLogo(
        drawable = drawable,
        size = logoSize,
        padding = QrVectorLogoPadding.Natural(LOGO_PADDING),
        shape = logoShape.shape,
        scale = logo?.scale ?: QrVectorLogo().scale,
    )
}

private fun QrStyle.vectorBackground(drawable: Drawable?): QrVectorBackground = QrVectorBackground(
    drawable = drawable,
    scale = backgroundImage?.scale ?: QrVectorBackground().scale,
    color = QrVectorColor.Solid(backgroundColor),
)

private fun QrStyle.highlighting(): QrHighlighting {
    if (!highlightAnchors) return QrHighlighting()
    return QrHighlighting(
        cornerEyes = HighlightingType.Default,
        versionEyes = HighlightingType.Default,
        timingLines = HighlightingType.Default,
    )
}

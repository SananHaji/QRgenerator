package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.ColorInt
import com.sananhaji.qrgenerator.qr.image.QrImageSource

/**
 * Everything the user can customise about a QR code, independent of its content.
 *
 * @property margin quiet zone around the code as a fraction of its size (0..0.5).
 * @property logoSize logo width as a fraction of the code size (up to 1/3).
 * @property highlightAnchors draws a light patch behind the eyes and timing lines,
 * which keeps the code scannable on top of a busy background picture.
 */
data class QrStyle(
    @ColorInt val darkColor: Int,
    @ColorInt val ballColor: Int,
    @ColorInt val frameColor: Int,
    @ColorInt val backgroundColor: Int,
    val pixelShape: PixelShapeOption,
    val ballShape: BallShapeOption,
    val frameShape: FrameShapeOption,
    val logo: QrImageSource?,
    val logoSize: Float,
    val logoShape: LogoShapeOption,
    val backgroundImage: QrImageSource?,
    val margin: Float,
    val highlightAnchors: Boolean,
)

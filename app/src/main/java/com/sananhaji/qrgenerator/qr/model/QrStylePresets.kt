package com.sananhaji.qrgenerator.qr.model

import android.graphics.Color

object QrStylePresets {

    private const val DEFAULT_MARGIN = .1f

    private const val DEFAULT_LOGO_SIZE = .25f

    /** Plain black-on-white code for users who just want a QR. */
    val SIMPLE = QrStyle(
        darkColor = Color.BLACK,
        ballColor = Color.BLACK,
        frameColor = Color.BLACK,
        backgroundColor = Color.WHITE,
        pixelShape = PixelShapeOption.SQUARE,
        ballShape = BallShapeOption.SQUARE,
        frameShape = FrameShapeOption.SQUARE,
        logo = null,
        logoSize = DEFAULT_LOGO_SIZE,
        logoShape = LogoShapeOption.CIRCLE,
        backgroundImage = null,
        margin = DEFAULT_MARGIN,
        highlightAnchors = false,
    )
}

package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.sananhaji.qrgenerator.R

private const val ROUNDED_LOGO_RADIUS = .25f

enum class LogoShapeOption(
    @StringRes override val labelRes: Int,
    val shape: QrVectorLogoShape,
) : ShapeOption {

    SQUARE(
        labelRes = R.string.shape_square,
        shape = QrVectorLogoShape.Default,
    ),

    ROUNDED(
        labelRes = R.string.shape_rounded,
        shape = QrVectorLogoShape.RoundCorners(ROUNDED_LOGO_RADIUS),
    ),

    CIRCLE(
        labelRes = R.string.shape_circle,
        shape = QrVectorLogoShape.Circle,
    ),

    DIAMOND(
        labelRes = R.string.shape_diamond,
        shape = QrVectorLogoShape.Rhombus,
    ),
}

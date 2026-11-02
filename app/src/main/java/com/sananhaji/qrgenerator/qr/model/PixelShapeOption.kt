package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.sananhaji.qrgenerator.R

private const val ROUNDED_PIXEL_RADIUS = .5f

enum class PixelShapeOption(
    @StringRes override val labelRes: Int,
    val shape: QrVectorPixelShape,
) : ShapeOption {

    SQUARE(
        labelRes = R.string.shape_square,
        shape = QrVectorPixelShape.Default,
    ),

    ROUNDED(
        labelRes = R.string.shape_rounded,
        shape = QrVectorPixelShape.RoundCorners(ROUNDED_PIXEL_RADIUS),
    ),

    CIRCLE(
        labelRes = R.string.shape_circle,
        shape = QrVectorPixelShape.Circle(),
    ),

    DIAMOND(
        labelRes = R.string.shape_diamond,
        shape = QrVectorPixelShape.Rhombus(),
    ),

    STAR(
        labelRes = R.string.shape_star,
        shape = QrVectorPixelShape.Star,
    ),

    VERTICAL(
        labelRes = R.string.shape_vertical,
        shape = QrVectorPixelShape.RoundCornersVertical(),
    ),

    HORIZONTAL(
        labelRes = R.string.shape_horizontal,
        shape = QrVectorPixelShape.RoundCornersHorizontal(),
    ),
}

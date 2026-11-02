package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.sananhaji.qrgenerator.R

private const val ROUNDED_BALL_RADIUS = .25f

/** Shape of the inner square of the three corner eyes. */
enum class BallShapeOption(
    @StringRes override val labelRes: Int,
    val shape: QrVectorBallShape,
) : ShapeOption {

    SQUARE(
        labelRes = R.string.shape_square,
        shape = QrVectorBallShape.Default,
    ),

    ROUNDED(
        labelRes = R.string.shape_rounded,
        shape = QrVectorBallShape.RoundCorners(ROUNDED_BALL_RADIUS),
    ),

    CIRCLE(
        labelRes = R.string.shape_circle,
        shape = QrVectorBallShape.Circle(),
    ),

    DIAMOND(
        labelRes = R.string.shape_diamond,
        shape = QrVectorBallShape.Rhombus(),
    ),
}

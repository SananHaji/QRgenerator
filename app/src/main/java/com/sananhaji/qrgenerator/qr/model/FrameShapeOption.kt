package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.sananhaji.qrgenerator.R

private const val ROUNDED_FRAME_CORNER = .25f

/** Shape of the outer ring of the three corner eyes. */
enum class FrameShapeOption(
    @StringRes override val labelRes: Int,
    val shape: QrVectorFrameShape,
) : ShapeOption {

    SQUARE(
        labelRes = R.string.shape_square,
        shape = QrVectorFrameShape.Default,
    ),

    ROUNDED(
        labelRes = R.string.shape_rounded,
        shape = QrVectorFrameShape.RoundCorners(ROUNDED_FRAME_CORNER),
    ),

    CIRCLE(
        labelRes = R.string.shape_circle,
        shape = QrVectorFrameShape.Circle(),
    ),
}

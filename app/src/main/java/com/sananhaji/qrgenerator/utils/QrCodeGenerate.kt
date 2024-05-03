package com.sananhaji.qrgenerator.utils

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColors
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import com.sananhaji.qrgenerator.R

class QrCodeGenerate(var text: String, var qrIcon: Drawable? = null, val startColor: Color) {


    @Composable
    fun BarcodeView() {
        val context = LocalContext.current
        val colors = listOf(
            0f to startColor.toArgb(),
            1f to ContextCompat.getColor(context, R.color.social_up_blue),
        )
        val options = QrVectorOptions.Builder().setPadding(.3f).setLogo(
            QrVectorLogo(
                drawable = ContextCompat.getDrawable(
                    context,
                    R.drawable.logoinrectangle
                ),
                size = .33f,
                padding = QrVectorLogoPadding.Natural(.1f),
                shape = QrVectorLogoShape.Circle
            )
        ).setColors(
            QrVectorColors(
                dark = QrVectorColor.LinearGradient(
                    colors = colors,
                    orientation = QrVectorColor.LinearGradient
                        .Orientation.LeftDiagonal
                ),
                ball = QrVectorColor.LinearGradient(
                    colors = colors,
                    orientation = QrVectorColor.LinearGradient
                        .Orientation.LeftDiagonal
                ),
                frame = QrVectorColor.LinearGradient(
                    colors = colors,
                    orientation = QrVectorColor.LinearGradient
                        .Orientation.LeftDiagonal
                )
            )
        ).setShapes(
            QrVectorShapes(
                darkPixel = QrVectorPixelShape.RoundCorners(.5f),
                ball = QrVectorBallShape.RoundCorners(.25f),
                frame = QrVectorFrameShape.RoundCorners(.25f),
            )
        ).build()

        val drawable: Drawable = QrCodeDrawable(
            { text }, options
        )
        qrIcon = drawable
        Image(
            bitmap = drawable.toBitmap(width = 500, height = 500).asImageBitmap(),
            contentDescription = "cert"
        )
    }

}
package com.sananhaji.qrgenerator.utils

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.alexzhirkevich.customqrgenerator.style.Color
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBackground
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

class QrCodeGenerate(var certId: String, var qrIcon: Drawable? = null) {


    @Composable
    fun BarcodeView() {
        val context = LocalContext.current
        val options = QrVectorOptions.Builder().setPadding(.3f).setLogo(
            QrVectorLogo(
                drawable = ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_logo_without_text_paint
                ),
                size = .25f,
                padding = QrVectorLogoPadding.Natural(.2f),
                shape = QrVectorLogoShape.Circle
            )
        ).setBackground(
            QrVectorBackground(
                drawable = ContextCompat.getDrawable(context, R.drawable.ic_qr_background),
            )
        ).setColors(
            QrVectorColors(
                dark = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.hhm_blue)
                ),
                ball = QrVectorColor.Solid(
                    ContextCompat.getColor(context, R.color.hhm_red)
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
            { Utils.baseUrl + certId }, options
        )
        qrIcon = drawable
        Image(
            bitmap = drawable.toBitmap(width = 500, height = 500).asImageBitmap(),
            contentDescription = "cert"
        )
    }

}
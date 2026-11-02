package com.sananhaji.qrgenerator.flavor

import android.graphics.Color
import com.sananhaji.qrgenerator.R
import com.sananhaji.qrgenerator.qr.image.QrImageSource
import com.sananhaji.qrgenerator.qr.model.BallShapeOption
import com.sananhaji.qrgenerator.qr.model.FrameShapeOption
import com.sananhaji.qrgenerator.qr.model.LogoShapeOption
import com.sananhaji.qrgenerator.qr.model.PixelShapeOption
import com.sananhaji.qrgenerator.qr.model.QrStyle
import com.sananhaji.qrgenerator.qr.model.QrTemplate

/** The certificate QR exactly as the app produced it before the Play Store rework. */
internal object HhmCertificateTemplate {

    private const val ID = "hhm_certificate"

    private const val CERTIFICATE_URL = "https://www.hhm.az/sertifikat-yoxla?certId="

    private const val ID_PLACEHOLDER = "HHMS321342"

    private const val BLUE = 0xFF062A49.toInt()

    private const val RED = 0xFF88122C.toInt()

    private const val MARGIN = .3f

    private const val LOGO_SIZE = .25f

    val TEMPLATE = QrTemplate(
        id = ID,
        nameRes = R.string.hhm_template_name,
        inputLabelRes = R.string.hhm_template_input_label,
        inputPlaceholder = ID_PLACEHOLDER,
        contentPrefix = CERTIFICATE_URL,
        uppercaseInput = true,
        style = QrStyle(
            darkColor = BLUE,
            ballColor = RED,
            frameColor = BLUE,
            backgroundColor = Color.TRANSPARENT,
            pixelShape = PixelShapeOption.ROUNDED,
            ballShape = BallShapeOption.ROUNDED,
            frameShape = FrameShapeOption.ROUNDED,
            logo = QrImageSource.Resource(R.drawable.ic_logo_without_text_paint),
            logoSize = LOGO_SIZE,
            logoShape = LogoShapeOption.CIRCLE,
            backgroundImage = QrImageSource.Resource(R.drawable.ic_qr_background),
            margin = MARGIN,
            highlightAnchors = false,
        ),
    )
}

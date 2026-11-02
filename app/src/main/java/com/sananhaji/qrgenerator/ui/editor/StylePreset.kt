package com.sananhaji.qrgenerator.ui.editor

import androidx.annotation.StringRes
import com.sananhaji.qrgenerator.qr.model.QrStyle

data class StylePreset(
    @StringRes val nameRes: Int,
    val style: QrStyle,
)

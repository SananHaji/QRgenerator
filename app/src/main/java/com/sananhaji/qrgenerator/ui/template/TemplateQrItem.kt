package com.sananhaji.qrgenerator.ui.template

import com.sananhaji.qrgenerator.ui.common.QrPreviewState

data class TemplateQrItem(
    val code: String,
    val preview: QrPreviewState,
)

package com.sananhaji.qrgenerator.ui.common

import com.sananhaji.qrgenerator.qr.model.QrStyle
import com.sananhaji.qrgenerator.qr.render.QrRenderer

suspend fun QrRenderer.renderPreview(
    content: String,
    style: QrStyle,
    sizePx: Int,
): QrPreviewState {
    if (content.isBlank()) return QrPreviewState.Empty
    val bitmap = render(
        content = content,
        style = style,
        sizePx = sizePx,
    )
    return bitmap?.let { QrPreviewState.Ready(it) } ?: QrPreviewState.TooLong
}

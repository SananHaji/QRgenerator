package com.sananhaji.qrgenerator.ui.editor

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

private const val RGB_MASK = 0xFFFFFF

private const val HEX_FORMAT = "#%06X"

/** `#RRGGBB`, ignoring alpha. */
fun Color.toHexLabel(): String = HEX_FORMAT.format(toArgb() and RGB_MASK)

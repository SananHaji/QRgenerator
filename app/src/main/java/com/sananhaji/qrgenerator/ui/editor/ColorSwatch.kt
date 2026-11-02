package com.sananhaji.qrgenerator.ui.editor

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val BORDER_WIDTH = 1.dp

private val SELECTED_BORDER_WIDTH = 3.dp

private val STRIKE_WIDTH = 2.dp

/** A round color sample; a transparent color is drawn crossed out. */
@Composable
fun ColorSwatch(
    color: Color,
    size: Dp,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (selected) SELECTED_BORDER_WIDTH else BORDER_WIDTH,
                color = borderColor,
                shape = CircleShape,
            )
            .clickable(onClick = onClick),
    ) {
        if (color.alpha == 0f) {
            val strikeColor = MaterialTheme.colorScheme.error
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawLine(
                    color = strikeColor,
                    start = Offset(x = 0f, y = this.size.height),
                    end = Offset(x = this.size.width, y = 0f),
                    strokeWidth = STRIKE_WIDTH.toPx(),
                )
            }
        }
    }
}

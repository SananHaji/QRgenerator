package com.sananhaji.qrgenerator.ui.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.sananhaji.qrgenerator.R

private val SWATCH_SIZE = 32.dp

private val WHEEL_HEIGHT = 220.dp

private val SLIDER_HEIGHT = 28.dp

private val SPACING = 12.dp

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    allowTransparent: Boolean,
    onConfirm: (Color) -> Unit,
    onDismiss: () -> Unit,
) {
    var picked by remember { mutableStateOf(initialColor) }
    val controller = rememberColorPickerController()
    val swatches = if (allowTransparent) ColorPalette.SWATCHES + Color.Transparent else ColorPalette.SWATCHES

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.color_dialog_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(SPACING)) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(SPACING)) {
                    items(swatches) { swatch ->
                        ColorSwatch(
                            color = swatch,
                            size = SWATCH_SIZE,
                            selected = swatch == picked,
                            onClick = {
                                picked = swatch
                                if (swatch.alpha > 0f) controller.selectByColor(swatch, fromUser = false)
                            },
                        )
                    }
                }
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(WHEEL_HEIGHT),
                    controller = controller,
                    initialColor = initialColor.takeIf { it.alpha > 0f },
                    onColorChanged = { envelope -> if (envelope.fromUser) picked = envelope.color },
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SLIDER_HEIGHT),
                    controller = controller,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SPACING),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ColorSwatch(
                        color = picked,
                        size = SWATCH_SIZE,
                        selected = false,
                        onClick = {},
                    )
                    Text(picked.toHexLabel())
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(picked) }) { Text(stringResource(R.string.action_ok)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        },
    )
}

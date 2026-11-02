package com.sananhaji.qrgenerator.ui.editor

import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

private val SWATCH_SIZE = 36.dp

private val ROW_PADDING = 4.dp

/** A labelled color sample that opens [ColorPickerDialog] on tap. */
@Composable
fun ColorSettingRow(
    @StringRes labelRes: Int,
    @ColorInt color: Int,
    allowTransparent: Boolean,
    onColorChange: (Int) -> Unit,
) {
    var dialogOpen by rememberSaveable { mutableStateOf(false) }
    val composeColor = Color(color)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { dialogOpen = true }
            .padding(vertical = ROW_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.bodyLarge,
        )
        ColorSwatch(
            color = composeColor,
            size = SWATCH_SIZE,
            selected = false,
            onClick = { dialogOpen = true },
        )
    }

    if (dialogOpen) {
        ColorPickerDialog(
            initialColor = composeColor,
            allowTransparent = allowTransparent,
            onConfirm = {
                onColorChange(it.toArgb())
                dialogOpen = false
            },
            onDismiss = { dialogOpen = false },
        )
    }
}

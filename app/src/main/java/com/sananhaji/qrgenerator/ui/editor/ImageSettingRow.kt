package com.sananhaji.qrgenerator.ui.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.R

private val BUTTON_SPACING = 8.dp

@Composable
fun ImageSettingRow(
    hasImage: Boolean,
    onPick: () -> Unit,
    onRemove: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(BUTTON_SPACING),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(if (hasImage) R.string.image_selected else R.string.image_none),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium,
        )
        if (hasImage) {
            TextButton(onClick = onRemove) { Text(stringResource(R.string.image_remove)) }
        }
        FilledTonalButton(onClick = onPick) {
            Text(stringResource(if (hasImage) R.string.image_change else R.string.image_pick))
        }
    }
}

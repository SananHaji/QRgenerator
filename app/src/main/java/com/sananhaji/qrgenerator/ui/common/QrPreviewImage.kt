package com.sananhaji.qrgenerator.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.R

private val MESSAGE_PADDING = 16.dp

@Composable
fun QrPreviewImage(
    state: QrPreviewState,
    size: Dp,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is QrPreviewState.Ready -> Image(
                bitmap = state.bitmap.asImageBitmap(),
                contentDescription = contentDescription,
            )

            QrPreviewState.Empty -> PreviewMessage(stringResource(R.string.editor_preview_empty))

            QrPreviewState.TooLong -> PreviewMessage(stringResource(R.string.editor_preview_too_long))
        }
    }
}

@Composable
private fun PreviewMessage(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(MESSAGE_PADDING),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
    )
}

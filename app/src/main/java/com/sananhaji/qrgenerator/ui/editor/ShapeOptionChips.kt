package com.sananhaji.qrgenerator.ui.editor

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.qr.model.ShapeOption

private val CHIP_SPACING = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : ShapeOption> ShapeOptionChips(
    @StringRes titleRes: Int,
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
) {
    Column {
        Text(
            text = stringResource(titleRes),
            style = MaterialTheme.typography.labelLarge,
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(CHIP_SPACING)) {
            items(options) { option ->
                FilterChip(
                    selected = option == selected,
                    onClick = { onSelect(option) },
                    label = { Text(stringResource(option.labelRes)) },
                )
            }
        }
    }
}

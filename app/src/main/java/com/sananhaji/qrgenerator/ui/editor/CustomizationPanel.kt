package com.sananhaji.qrgenerator.ui.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.R
import com.sananhaji.qrgenerator.qr.model.BallShapeOption
import com.sananhaji.qrgenerator.qr.model.FrameShapeOption
import com.sananhaji.qrgenerator.qr.model.LogoShapeOption
import com.sananhaji.qrgenerator.qr.model.PixelShapeOption
import com.sananhaji.qrgenerator.qr.model.QrStyle

private val SECTION_SPACING = 12.dp

private val CHIP_SPACING = 8.dp

/** The library caps the logo at a third of the code; below a tenth it is unreadable. */
private val LOGO_SIZE_RANGE = .1f..(1f / 3f)

private val MARGIN_RANGE = 0f..(.4f)

@Composable
fun CustomizationPanel(
    style: QrStyle,
    presets: List<StylePreset>,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
    onPickLogo: () -> Unit,
    onPickBackground: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(SECTION_SPACING)) {
        PresetsSection(
            style = style,
            presets = presets,
            onStyleChange = onStyleChange,
        )
        ColorsSection(
            style = style,
            onStyleChange = onStyleChange,
        )
        ShapesSection(
            style = style,
            onStyleChange = onStyleChange,
        )
        LogoSection(
            style = style,
            onStyleChange = onStyleChange,
            onPickLogo = onPickLogo,
        )
        BackgroundSection(
            style = style,
            onStyleChange = onStyleChange,
            onPickBackground = onPickBackground,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PresetsSection(
    style: QrStyle,
    presets: List<StylePreset>,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
) {
    SettingsSection(titleRes = R.string.section_presets) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(CHIP_SPACING)) {
            items(presets) { preset ->
                FilterChip(
                    selected = preset.style == style,
                    onClick = { onStyleChange { preset.style } },
                    label = { Text(stringResource(preset.nameRes)) },
                )
            }
        }
    }
}

@Composable
private fun ColorsSection(
    style: QrStyle,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
) {
    SettingsSection(titleRes = R.string.section_colors) {
        ColorSettingRow(
            labelRes = R.string.color_pixels,
            color = style.darkColor,
            allowTransparent = false,
            onColorChange = { color -> onStyleChange { it.copy(darkColor = color) } },
        )
        ColorSettingRow(
            labelRes = R.string.color_eye_frame,
            color = style.frameColor,
            allowTransparent = false,
            onColorChange = { color -> onStyleChange { it.copy(frameColor = color) } },
        )
        ColorSettingRow(
            labelRes = R.string.color_eye_ball,
            color = style.ballColor,
            allowTransparent = false,
            onColorChange = { color -> onStyleChange { it.copy(ballColor = color) } },
        )
        ColorSettingRow(
            labelRes = R.string.color_background,
            color = style.backgroundColor,
            allowTransparent = true,
            onColorChange = { color -> onStyleChange { it.copy(backgroundColor = color) } },
        )
    }
}

@Composable
private fun ShapesSection(
    style: QrStyle,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
) {
    SettingsSection(titleRes = R.string.section_shapes) {
        ShapeOptionChips(
            titleRes = R.string.shape_pixels,
            options = PixelShapeOption.entries,
            selected = style.pixelShape,
            onSelect = { shape -> onStyleChange { it.copy(pixelShape = shape) } },
        )
        ShapeOptionChips(
            titleRes = R.string.shape_eye_frame,
            options = FrameShapeOption.entries,
            selected = style.frameShape,
            onSelect = { shape -> onStyleChange { it.copy(frameShape = shape) } },
        )
        ShapeOptionChips(
            titleRes = R.string.shape_eye_ball,
            options = BallShapeOption.entries,
            selected = style.ballShape,
            onSelect = { shape -> onStyleChange { it.copy(ballShape = shape) } },
        )
    }
}

@Composable
private fun LogoSection(
    style: QrStyle,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
    onPickLogo: () -> Unit,
) {
    SettingsSection(titleRes = R.string.section_logo) {
        ImageSettingRow(
            hasImage = style.logo != null,
            onPick = onPickLogo,
            onRemove = { onStyleChange { it.copy(logo = null) } },
        )
        if (style.logo != null) {
            LabeledSlider(
                labelRes = R.string.logo_size,
                value = style.logoSize,
                range = LOGO_SIZE_RANGE,
                onValueChange = { size -> onStyleChange { it.copy(logoSize = size) } },
            )
            ShapeOptionChips(
                titleRes = R.string.shape_logo,
                options = LogoShapeOption.entries,
                selected = style.logoShape,
                onSelect = { shape -> onStyleChange { it.copy(logoShape = shape) } },
            )
        }
    }
}

@Composable
private fun BackgroundSection(
    style: QrStyle,
    onStyleChange: ((QrStyle) -> QrStyle) -> Unit,
    onPickBackground: () -> Unit,
) {
    SettingsSection(titleRes = R.string.section_background) {
        ImageSettingRow(
            hasImage = style.backgroundImage != null,
            onPick = onPickBackground,
            onRemove = { onStyleChange { it.copy(backgroundImage = null) } },
        )
        if (style.backgroundImage != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.highlight_anchors),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Switch(
                    checked = style.highlightAnchors,
                    onCheckedChange = { checked -> onStyleChange { it.copy(highlightAnchors = checked) } },
                )
            }
        }
        LabeledSlider(
            labelRes = R.string.margin,
            value = style.margin,
            range = MARGIN_RANGE,
            onValueChange = { margin -> onStyleChange { it.copy(margin = margin) } },
        )
    }
}

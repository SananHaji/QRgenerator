package com.sananhaji.qrgenerator.ui.editor

import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sananhaji.qrgenerator.R
import com.sananhaji.qrgenerator.flavor.FlavorConfig
import com.sananhaji.qrgenerator.qr.export.QrSharer
import com.sananhaji.qrgenerator.qr.image.QrImageSource
import com.sananhaji.qrgenerator.qr.model.QrStylePresets
import com.sananhaji.qrgenerator.review.InAppReviewLauncher
import com.sananhaji.qrgenerator.ui.common.LoadingOverlay
import com.sananhaji.qrgenerator.ui.common.QrPreviewImage
import com.sananhaji.qrgenerator.ui.common.QrPreviewState
import com.sananhaji.qrgenerator.ui.common.rememberStorageWriteAccess
import kotlinx.coroutines.launch

private const val IMAGE_MIME_TYPE = "image/*"

private val PREVIEW_SIZE = 260.dp

private val SCREEN_PADDING = 16.dp

private val SPACING = 12.dp

/**
 * One editor for both entry points: the simple flow starts with customization folded away,
 * so a user who only wants a plain code sees a text field, a preview and two buttons.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrEditorScreen(
    startCustomizing: Boolean,
    onBack: () -> Unit,
    viewModel: QrEditorViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val storageAccess = rememberStorageWriteAccess()
    val sharer = remember(context) { QrSharer(context) }
    val activity = LocalActivity.current
    val reviewLauncher = remember(activity) { activity?.let(::InAppReviewLauncher) }
    val preview by viewModel.preview.collectAsState()
    var customizing by rememberSaveable { mutableStateOf(startCustomizing) }

    val presets = remember {
        listOf(StylePreset(nameRes = R.string.preset_simple, style = QrStylePresets.SIMPLE)) +
            FlavorConfig.templates.map { StylePreset(nameRes = it.nameRes, style = it.style) }
    }
    val savedMessage = stringResource(R.string.saved_one)
    val failedMessage = stringResource(R.string.save_failed)
    val permissionMessage = stringResource(R.string.permission_needed)
    val shareTitle = stringResource(R.string.share_chooser_title)

    val logoPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.updateStyle { style -> style.copy(logo = QrImageSource.Picked(it)) } }
    }
    val backgroundPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.updateStyle { style ->
                style.copy(
                    backgroundImage = QrImageSource.Picked(it),
                    highlightAnchors = true,
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(if (customizing) R.string.editor_title_custom else R.string.editor_title_simple))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null) }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(SCREEN_PADDING),
                verticalArrangement = Arrangement.spacedBy(SPACING),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ElevatedCard {
                    QrPreviewImage(
                        state = preview,
                        size = PREVIEW_SIZE,
                        contentDescription = viewModel.content,
                    )
                }

                OutlinedTextField(
                    value = viewModel.content,
                    onValueChange = viewModel::updateContent,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.editor_content_label)) },
                    placeholder = { Text(stringResource(R.string.editor_content_placeholder)) },
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SPACING),
                ) {
                    Button(
                        onClick = {
                            if (!storageAccess.ensureGranted()) {
                                scope.launch { snackbarHostState.showSnackbar(permissionMessage) }
                                return@Button
                            }
                            scope.launch {
                                val saved = viewModel.saveToGallery()
                                if (saved && viewModel.takeReviewRequest()) reviewLauncher?.launch()
                                snackbarHostState.showSnackbar(if (saved) savedMessage else failedMessage)
                            }
                        },
                        enabled = preview is QrPreviewState.Ready,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(stringResource(R.string.action_save))
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                viewModel.exportForShare()?.let {
                                    sharer.share(
                                        bitmap = it,
                                        chooserTitle = shareTitle,
                                    )
                                }
                            }
                        },
                        enabled = preview is QrPreviewState.Ready,
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = stringResource(R.string.action_share))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(onClick = { customizing = !customizing }) {
                        Text(
                            stringResource(
                                if (customizing) R.string.editor_customize_hide else R.string.editor_customize_show,
                            ),
                        )
                    }
                    if (viewModel.style != QrStylePresets.SIMPLE) {
                        TextButton(onClick = { viewModel.updateStyle { QrStylePresets.SIMPLE } }) {
                            Text(stringResource(R.string.editor_reset))
                        }
                    }
                }

                AnimatedVisibility(visible = customizing) {
                    CustomizationPanel(
                        style = viewModel.style,
                        presets = presets,
                        onStyleChange = viewModel::updateStyle,
                        onPickLogo = { logoPicker.launch(IMAGE_MIME_TYPE) },
                        onPickBackground = { backgroundPicker.launch(IMAGE_MIME_TYPE) },
                    )
                }
            }

            if (viewModel.isBusy) LoadingOverlay()
        }
    }
}

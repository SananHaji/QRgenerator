package com.sananhaji.qrgenerator.ui.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sananhaji.qrgenerator.R
import com.sananhaji.qrgenerator.ui.common.LoadingOverlay
import com.sananhaji.qrgenerator.ui.common.QrPreviewImage
import com.sananhaji.qrgenerator.ui.common.rememberStorageWriteAccess
import kotlinx.coroutines.launch

private val PREVIEW_SIZE = 180.dp

private val SCREEN_PADDING = 16.dp

private val SPACING = 12.dp

private const val INPUT_MIN_LINES = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateBatchScreen(
    onBack: () -> Unit,
    viewModel: TemplateBatchViewModel = viewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val storageAccess = rememberStorageWriteAccess()
    val template = viewModel.template
    val permissionMessage = stringResource(R.string.permission_needed)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(template.nameRes)) },
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(SCREEN_PADDING),
                verticalArrangement = Arrangement.spacedBy(SPACING),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    OutlinedTextField(
                        value = viewModel.input,
                        onValueChange = viewModel::updateInput,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(template.inputLabelRes)) },
                        placeholder = { Text(template.inputPlaceholder) },
                        supportingText = { Text(stringResource(R.string.template_input_hint)) },
                        minLines = INPUT_MIN_LINES,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                    )
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SPACING),
                    ) {
                        FilledTonalButton(
                            onClick = viewModel::generate,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.action_generate))
                        }
                        Button(
                            onClick = {
                                if (!storageAccess.ensureGranted()) {
                                    scope.launch { snackbarHostState.showSnackbar(permissionMessage) }
                                    return@Button
                                }
                                scope.launch {
                                    val summary = viewModel.saveAll()
                                    snackbarHostState.showSnackbar(
                                        context.getString(R.string.saved_many, summary.saved, summary.total),
                                    )
                                }
                            },
                            enabled = viewModel.items.isNotEmpty(),
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.action_save_all))
                        }
                    }
                }
                if (viewModel.items.isEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.template_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
                items(viewModel.items, key = { it.code }) { item ->
                    ElevatedCard {
                        QrPreviewImage(
                            state = item.preview,
                            size = PREVIEW_SIZE,
                            contentDescription = item.code,
                        )
                        Text(
                            text = item.code,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = SPACING),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            }

            if (viewModel.isBusy) LoadingOverlay()
        }
    }
}

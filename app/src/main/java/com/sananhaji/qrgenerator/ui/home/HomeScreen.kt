package com.sananhaji.qrgenerator.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sananhaji.qrgenerator.R
import com.sananhaji.qrgenerator.flavor.FlavorConfig
import com.sananhaji.qrgenerator.qr.model.QrTemplate

private val SCREEN_PADDING = 16.dp

private val SPACING = 16.dp

/** Google Play requires the privacy policy to be reachable from inside the app, not only from the listing. */
private const val PRIVACY_POLICY_URL = "https://sananhaji.github.io/QRgenerator/privacy-policy.html"

@Composable
fun HomeScreen(
    onSimple: () -> Unit,
    onCustom: () -> Unit,
    onTemplate: (QrTemplate) -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .verticalScroll(rememberScrollState())
            .padding(SCREEN_PADDING),
        verticalArrangement = Arrangement.spacedBy(SPACING),
    ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineMedium,
        )
        HomeCard(
            title = stringResource(R.string.home_simple_title),
            description = stringResource(R.string.home_simple_description),
            onClick = onSimple,
        )
        HomeCard(
            title = stringResource(R.string.home_custom_title),
            description = stringResource(R.string.home_custom_description),
            onClick = onCustom,
        )
        if (FlavorConfig.templates.isNotEmpty()) {
            Text(
                text = stringResource(R.string.home_templates_title),
                style = MaterialTheme.typography.titleMedium,
            )
            FlavorConfig.templates.forEach { template ->
                HomeCard(
                    title = stringResource(template.nameRes),
                    description = stringResource(R.string.home_template_description),
                    onClick = { onTemplate(template) },
                )
            }
        }
        TextButton(onClick = { uriHandler.openUri(PRIVACY_POLICY_URL) }) {
            Text(stringResource(R.string.home_privacy_policy))
        }
    }
}

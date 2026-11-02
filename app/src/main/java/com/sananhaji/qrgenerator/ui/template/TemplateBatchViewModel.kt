package com.sananhaji.qrgenerator.ui.template

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sananhaji.qrgenerator.analytics.AnalyticsEvent
import com.sananhaji.qrgenerator.analytics.AppAnalytics
import com.sananhaji.qrgenerator.analytics.FirebaseAppAnalytics
import com.sananhaji.qrgenerator.flavor.FlavorConfig
import com.sananhaji.qrgenerator.qr.export.QrGallerySaver
import com.sananhaji.qrgenerator.qr.image.QrImageLoader
import com.sananhaji.qrgenerator.qr.model.QrTemplate
import com.sananhaji.qrgenerator.qr.render.QrRenderer
import com.sananhaji.qrgenerator.ui.common.QrFileName
import com.sananhaji.qrgenerator.ui.common.QrRenderSizes
import com.sananhaji.qrgenerator.ui.common.renderPreview
import com.sananhaji.qrgenerator.ui.navigation.Routes
import kotlinx.coroutines.launch

/** Turns a list of IDs into codes of one [QrTemplate], one or many at a time. */
class TemplateBatchViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

    val template: QrTemplate = FlavorConfig.templates.first {
        it.id == savedStateHandle.get<String>(Routes.ARG_TEMPLATE_ID)
    }

    private val renderer = QrRenderer(QrImageLoader(application))

    private val gallerySaver = QrGallerySaver(application)

    private val analytics: AppAnalytics = FirebaseAppAnalytics(application)

    var input by mutableStateOf("")
        private set

    var items by mutableStateOf<List<TemplateQrItem>>(emptyList())
        private set

    var isBusy by mutableStateOf(false)
        private set

    fun updateInput(value: String) {
        input = value
    }

    fun generate() {
        val codes = parseCodes()
        viewModelScope.launch {
            items = codes.map { code ->
                TemplateQrItem(
                    code = code,
                    preview = renderer.renderPreview(
                        content = template.contentFor(code),
                        style = template.style,
                        sizePx = QrRenderSizes.LIST_PREVIEW_PX,
                    ),
                )
            }
        }
    }

    suspend fun saveAll(): SaveSummary {
        isBusy = true
        try {
            val saved = items.count { saveOne(it.code) }
            analytics.log(
                AnalyticsEvent.TemplateCodesSaved(
                    templateId = template.id,
                    count = saved,
                ),
            )
            return SaveSummary(
                saved = saved,
                total = items.size,
            )
        } finally {
            isBusy = false
        }
    }

    private suspend fun saveOne(code: String): Boolean {
        val bitmap = renderer.render(
            content = template.contentFor(code),
            style = template.style,
            sizePx = QrRenderSizes.EXPORT_PX,
        ) ?: return false
        return gallerySaver.save(
            bitmap = bitmap,
            name = QrFileName.forCode(code),
        )
    }

    private fun parseCodes(): List<String> = input
        .split(*CODE_SEPARATORS)
        .map(template::normalize)
        .filter { it.isNotEmpty() }
        .distinct()

    private companion object {

        val CODE_SEPARATORS = arrayOf(",", "\n", ";")
    }
}

package com.sananhaji.qrgenerator.ui.editor

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sananhaji.qrgenerator.qr.export.QrGallerySaver
import com.sananhaji.qrgenerator.qr.image.QrImageLoader
import com.sananhaji.qrgenerator.qr.model.QrStyle
import com.sananhaji.qrgenerator.qr.model.QrStylePresets
import com.sananhaji.qrgenerator.qr.render.QrRenderer
import com.sananhaji.qrgenerator.ui.common.QrFileName
import com.sananhaji.qrgenerator.ui.common.QrPreviewState
import com.sananhaji.qrgenerator.ui.common.QrRenderSizes
import com.sananhaji.qrgenerator.ui.common.renderPreview
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class QrEditorViewModel(application: Application) : AndroidViewModel(application) {

    private val renderer = QrRenderer(QrImageLoader(application))

    private val gallerySaver = QrGallerySaver(application)

    var content by mutableStateOf("")
        private set

    var style by mutableStateOf(QrStylePresets.SIMPLE)
        private set

    var isBusy by mutableStateOf(false)
        private set

    /** Re-rendered whenever content or style settle, cancelling a render that became stale. */
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val preview: StateFlow<QrPreviewState> = snapshotFlow { content to style }
        .debounce(PREVIEW_DEBOUNCE)
        .mapLatest { (content, style) ->
            renderer.renderPreview(
                content = content,
                style = style,
                sizePx = QrRenderSizes.PREVIEW_PX,
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT.inWholeMilliseconds),
            initialValue = QrPreviewState.Empty,
        )

    fun updateContent(value: String) {
        content = value
    }

    fun updateStyle(transform: (QrStyle) -> QrStyle) {
        style = transform(style)
    }

    suspend fun saveToGallery(): Boolean = runBusy {
        val bitmap = exportBitmap() ?: return@runBusy false
        gallerySaver.save(
            bitmap = bitmap,
            name = QrFileName.timestamped(),
        )
    }

    suspend fun exportForShare(): Bitmap? = runBusy { exportBitmap() }

    private suspend fun exportBitmap(): Bitmap? =
        content.takeIf { it.isNotBlank() }?.let {
            renderer.render(
                content = it,
                style = style,
                sizePx = QrRenderSizes.EXPORT_PX,
            )
        }

    private suspend fun <T> runBusy(block: suspend () -> T): T {
        isBusy = true
        return try {
            block()
        } finally {
            isBusy = false
        }
    }

    private companion object {

        val PREVIEW_DEBOUNCE = 150.milliseconds

        val STOP_TIMEOUT = 5.seconds
    }
}

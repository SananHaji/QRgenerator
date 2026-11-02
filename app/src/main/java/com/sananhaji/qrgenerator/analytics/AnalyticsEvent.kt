package com.sananhaji.qrgenerator.analytics

import com.sananhaji.qrgenerator.qr.model.QrStyle
import com.sananhaji.qrgenerator.qr.model.QrStylePresets

private const val PARAM_MODE = "mode"

private const val PARAM_HAS_LOGO = "has_logo"

private const val PARAM_HAS_BACKGROUND = "has_background"

private const val PARAM_TEMPLATE_ID = "template_id"

private const val PARAM_COUNT = "count"

private const val MODE_SIMPLE = "simple"

private const val MODE_CUSTOM = "custom"

private const val FLAG_SET = 1L

private const val FLAG_UNSET = 0L

/**
 * An analytics event and its parameters. Each event carries its own name and values,
 * so adding one never touches the reporting code. Values are `String` or `Long`,
 * the types Google Analytics accepts.
 */
sealed interface AnalyticsEvent {

    val name: String

    val params: Map<String, Any>

    data class QrSaved(val style: QrStyle) : AnalyticsEvent {

        override val name: String get() = "qr_saved"

        override val params: Map<String, Any> get() = style.toParams()
    }

    data class QrShared(val style: QrStyle) : AnalyticsEvent {

        override val name: String get() = "qr_shared"

        override val params: Map<String, Any> get() = style.toParams()
    }

    data class TemplateCodesSaved(
        val templateId: String,
        val count: Int,
    ) : AnalyticsEvent {

        override val name: String get() = "template_codes_saved"

        override val params: Map<String, Any>
            get() = mapOf(
                PARAM_TEMPLATE_ID to templateId,
                PARAM_COUNT to count.toLong(),
            )
    }
}

/** Only which features were used is reported; the encoded content never leaves the device. */
private fun QrStyle.toParams(): Map<String, Any> = mapOf(
    PARAM_MODE to if (this == QrStylePresets.SIMPLE) MODE_SIMPLE else MODE_CUSTOM,
    PARAM_HAS_LOGO to (logo != null).toFlag(),
    PARAM_HAS_BACKGROUND to (backgroundImage != null).toFlag(),
)

private fun Boolean.toFlag(): Long = if (this) FLAG_SET else FLAG_UNSET

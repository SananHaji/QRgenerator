package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes

/**
 * A ready-made style plus a fixed content prefix, for organisations that print
 * many codes of the same kind (for example certificate links) by typing only an ID.
 */
data class QrTemplate(
    val id: String,
    @StringRes val nameRes: Int,
    @StringRes val inputLabelRes: Int,
    val inputPlaceholder: String,
    val contentPrefix: String,
    val uppercaseInput: Boolean,
    val style: QrStyle,
) {

    fun normalize(input: String): String = input.trim().let { if (uppercaseInput) it.uppercase() else it }

    fun contentFor(code: String): String = contentPrefix + code
}

package com.sananhaji.qrgenerator.ui.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Gallery file names (without extension) for saved codes. */
object QrFileName {

    private const val PREFIX = "QR_"

    private const val TIMESTAMP_PATTERN = "yyyyMMdd_HHmmss"

    private const val REPLACEMENT = "_"

    private val UNSAFE_CHARACTERS = Regex("[^A-Za-z0-9._-]")

    fun timestamped(): String = PREFIX + SimpleDateFormat(TIMESTAMP_PATTERN, Locale.US).format(Date())

    fun forCode(code: String): String = code.replace(UNSAFE_CHARACTERS, REPLACEMENT)
}

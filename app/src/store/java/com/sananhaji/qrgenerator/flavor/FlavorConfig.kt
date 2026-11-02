package com.sananhaji.qrgenerator.flavor

import com.sananhaji.qrgenerator.qr.model.QrTemplate

/** Public Play Store build: no company templates and no log sender. */
object FlavorConfig {

    const val LOG_SENDER_ENABLED = false

    val templates: List<QrTemplate> = emptyList()
}

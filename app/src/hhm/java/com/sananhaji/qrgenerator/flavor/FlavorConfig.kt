package com.sananhaji.qrgenerator.flavor

import com.sananhaji.qrgenerator.qr.model.QrTemplate

/** Company build for HHM: certificate template on the home screen and the log sender. */
object FlavorConfig {

    const val LOG_SENDER_ENABLED = true

    val templates: List<QrTemplate> = listOf(HhmCertificateTemplate.TEMPLATE)
}

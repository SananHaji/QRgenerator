package com.sananhaji.qrgenerator.qr.model

import androidx.annotation.StringRes

/**
 * A shape the user can pick in the editor; each entry carries its own label,
 * so the UI lists any option set without knowing its members.
 */
interface ShapeOption {

    @get:StringRes
    val labelRes: Int
}

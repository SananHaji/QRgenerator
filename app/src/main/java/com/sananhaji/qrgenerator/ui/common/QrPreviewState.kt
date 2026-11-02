package com.sananhaji.qrgenerator.ui.common

import android.graphics.Bitmap

sealed interface QrPreviewState {

    object Empty : QrPreviewState

    object TooLong : QrPreviewState

    data class Ready(val bitmap: Bitmap) : QrPreviewState
}

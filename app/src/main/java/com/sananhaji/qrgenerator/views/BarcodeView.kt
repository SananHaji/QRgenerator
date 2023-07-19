package com.sananhaji.qrgenerator.views

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType


@Composable
fun BarcodeView(url: String) {
    if (BarcodeType.QR_CODE.isValueValid(url)) {
        Barcode(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(3.dp),
            resolutionFactor = 10, // Optionally, increase the resolution of the generated image
            type = BarcodeType.QR_CODE, // pick the type of barcode you want to render
            value = url // The textual representation of this code
        )
    }

    if (!BarcodeType.CODE_128.isValueValid(url)) {
        Text("this is not code 128 compatible")
    }
}

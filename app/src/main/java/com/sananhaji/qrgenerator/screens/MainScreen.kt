package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.rememberScreenshotState
import java.io.File
import java.io.FileOutputStream


val placeHolder = "HHMS321342"
val label = "Sertifikat ID"
val baseUrl = "https://www.hhm.az/sertifikat-yoxla?certId="


private const val TAG = "MainScreeaan"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen() {
    val certID = remember { mutableStateOf("") }
    val url = remember { mutableStateOf("") }

    val screenShotState = rememberScreenshotState()

    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = certID.value,
            onValueChange = {
                val id = it.uppercase()
                url.value = baseUrl + id
                certID.value = id
            },
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(textColor = Color.Black)
        )

        ScreenshotBox(screenshotState = screenShotState) {
            BarcodeView(url = url.value)
        }

        Button(
            onClick = {
                screenShotState.capture()
                Log.d(TAG, "MainScreen: ${screenShotState.bitmap}")
                Log.d(TAG, "MainScreen: ${permissionState.hasPermission}")
                if (permissionState.hasPermission.not()) {
                    permissionState.launchPermissionRequest()
                } else {
                    screenShotState.bitmap?.let { saveImage(it, certID.value) }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save Image")
        }

    }

}

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

fun saveImage(bitmap: Bitmap, name: String) {
    val filePath = Environment.getExternalStorageDirectory().absolutePath +
            "/QRCodeGenerator/"
    val dir = File(filePath)
    if (!dir.exists()) dir.mkdirs()
    val file = File(dir, "$name.png")
    val fOut = FileOutputStream(file)

    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
    fOut.flush()
    fOut.close()
}
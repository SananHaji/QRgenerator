package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sananhaji.qrgenerator.utils.QrCodeGenerate
import com.sananhaji.qrgenerator.utils.Utils

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeRelease(navHostController: NavHostController) {
    val controller = rememberColorPickerController()
    var text by remember { mutableStateOf("") }
    val selectedStartColor = remember { mutableStateOf(Color.Blue) }
    val writePermissionState = rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val readPermissionStateOld = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    val readPermissionStateNew = rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)

    val qr = remember { mutableStateOf(QrCodeGenerate(text = "", startColor = selectedStartColor.value)) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = qr.value.text,
            singleLine = true,
            onValueChange = {
                text = it
                qr.value.text = it
                qr.value = QrCodeGenerate(text = it, startColor = selectedStartColor.value)
            },
            label = { Text(text = "Input text") },
            placeholder = { Text(text = Utils.baseUrl) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        qr.value.BarcodeView()

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                selectedStartColor.value = colorEnvelope.color
                qr.value = QrCodeGenerate(text = text, startColor = selectedStartColor.value)
            }
        )
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
        )

        AlphaTile(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp)),
            controller = controller
        )

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S && writePermissionState.hasPermission.not()) {
                    writePermissionState.launchPermissionRequest()
                } else {
                    qr.value.qrIcon?.let {
                        val bitmap = it.toBitmap(1000, 1000)
                        Log.d("TAGTAGTAGTAGTAG", "HomeRelease: " + text)
                        if (text.isBlank()) text = "SocialUp"
                        Utils.saveImage(bitmap, text)
                    }
                    text = ""
                    qr.value.text = ""
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save Image")
        }
    }

}

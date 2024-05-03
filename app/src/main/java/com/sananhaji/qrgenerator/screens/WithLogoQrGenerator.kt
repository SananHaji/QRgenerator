package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sananhaji.qrgenerator.utils.QrCodeGenerate
import com.sananhaji.qrgenerator.utils.Utils

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithLogoQrGenerator(navHostController: NavHostController) {
    val controller = rememberColorPickerController()
    val selectedStartColor = remember { mutableStateOf(Color.Blue) }
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val qr = remember { mutableStateOf(QrCodeGenerate(text = "", startColor = selectedStartColor.value)) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = qr.value.text,
            onValueChange = {
                val id = it.uppercase()
                qr.value.text = id
                qr.value = QrCodeGenerate(text = id, startColor = selectedStartColor.value)
            },
            label = { Text(text = Utils.label) },
            placeholder = { Text(text = Utils.placeHolder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        qr.value.BarcodeView()

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S && permissionState.hasPermission.not()) {
                    permissionState.launchPermissionRequest()
                } else {
                    qr.value.qrIcon?.let {
                        val bitmap = it.toBitmap(1000, 1000)
                        Utils.saveImage(bitmap, qr.value.text)
                    }
                    navHostController.popBackStack()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save Image")
        }
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                selectedStartColor.value = colorEnvelope.color
                Log.d("TAGTAGTAGTAG", "WithLogoQrGenerator: "+colorEnvelope)
            }
        )
    }

}

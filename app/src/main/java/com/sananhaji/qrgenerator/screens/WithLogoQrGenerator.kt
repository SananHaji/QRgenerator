package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sananhaji.qrgenerator.utils.QrCodeGenerate
import com.sananhaji.qrgenerator.utils.Utils

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithLogoQrGenerator(navHostController: NavHostController) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val qr = remember { mutableStateOf(QrCodeGenerate("")) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = qr.value.certId,
            onValueChange = {
                val id = it.uppercase()
                qr.value.certId = id
                qr.value = QrCodeGenerate(id)
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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R && permissionState.hasPermission.not()) {
                    permissionState.launchPermissionRequest()
                } else {
                    qr.value.qrIcon?.let { Utils.saveImage(it.toBitmap(500, 500), qr.value.certId) }
                    navHostController.popBackStack()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save Image")
        }
    }

}

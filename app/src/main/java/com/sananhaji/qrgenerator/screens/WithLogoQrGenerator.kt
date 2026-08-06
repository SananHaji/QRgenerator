package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sananhaji.qrgenerator.utils.QrCodeGenerate
import com.sananhaji.qrgenerator.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WithLogoQrGenerator(navHostController: NavHostController) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val qr = remember { mutableStateOf(QrCodeGenerate("")) }
    val isLoading = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
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
                        scope.launch {
                            isLoading.value = true
                            var allSuccess = false
                            withContext(Dispatchers.IO) {
                                qr.value.qrIcon?.let { 
                                    allSuccess = Utils.saveImage(context, it.toBitmap(500, 500), qr.value.certId) 
                                }
                            }
                            if (allSuccess) {
                                navHostController.popBackStack()
                            }
                            isLoading.value = false
                        }
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Save Image")
            }
        }

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}


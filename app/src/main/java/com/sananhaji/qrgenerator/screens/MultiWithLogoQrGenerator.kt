package com.sananhaji.qrgenerator.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiWithLogoQrGenerator(navHostController: NavHostController) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val text = remember { mutableStateOf("") }

    val context = LocalContext.current
    val qrList = remember { mutableStateListOf(QrCodeGenerate("")) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = { Text(text = "Multi Certificate IDs") },
            placeholder = { Text(text = "${Utils.placeHolder}, ${Utils.placeHolder}, ${Utils.placeHolder}") }
        )

        Text(text = "Separate IDs with comma")

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(qrList) { qr ->
                    if (qr.certId.isNotEmpty()) {
                        qr.BarcodeView()
                        Text(text = qr.certId)
                        Divider(color = Color.Blue, thickness = 1.dp)
                    }
                }
            }

            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    val ids = text.value.split(",")
                    qrList.clear()
                    qrList.addAll(ids.map { QrCodeGenerate(it) })
                },
                containerColor = Color.Green,
                shape = RoundedCornerShape(8.dp),
                text = {
                    Text(
                        text = "Create QRs",
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = "Create QRs",
                        tint = Color.White,
                    )
                }
            )
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                onClick = {
                    if (permissionState.hasPermission.not() && Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                        permissionState.launchPermissionRequest()
                    } else {
                        qrList.toList()
                            .onEach {
                                it.qrIcon?.let { it1 ->
                                    Utils.saveImage(
                                        it1.toBitmap(500, 500),
                                        it.certId
                                    )
                                }
                            }
                        navHostController.popBackStack()
                    }
                },
                containerColor = Color.Green,
                shape = RoundedCornerShape(8.dp),
                text = {
                    Text(
                        text = "Save QRs",
                        color = Color.White
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Save QRs",
                        tint = Color.White,
                    )
                }
            )
        }
    }
}


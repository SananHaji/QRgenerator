package com.sananhaji.qrgenerator.screens

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.sananhaji.qrgenerator.utils.Utils
import com.sananhaji.qrgenerator.utils.Utils.baseUrl
import com.sananhaji.qrgenerator.utils.Utils.label
import com.sananhaji.qrgenerator.utils.Utils.placeHolder
import com.sananhaji.qrgenerator.views.BarcodeView
import com.smarttoolfactory.screenshot.ScreenshotBox
import com.smarttoolfactory.screenshot.rememberScreenshotState


private const val TAG = "MainScreeaan"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SingleQrGenerator(navHostController: NavHostController) {
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
            placeholder = { Text(text = placeHolder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = TextFieldDefaults.textFieldColors(focusedTextColor = Color.Black)
        )

        ScreenshotBox(screenshotState = screenShotState) {
            BarcodeView(url = url.value)
        }

        Button(
            onClick = {
                screenShotState.capture()
                if (permissionState.hasPermission.not()) {
                    permissionState.launchPermissionRequest()
                } else {
                    screenShotState.bitmap?.let {
                        Utils.saveImage(it, certID.value)
                        navHostController.popBackStack()
                    }
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Save Image")
        }
    }
}